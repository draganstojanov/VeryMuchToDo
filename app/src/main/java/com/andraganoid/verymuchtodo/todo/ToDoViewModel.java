package com.andraganoid.verymuchtodo.todo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.model.ToDoLocation;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.model.User;
import com.andraganoid.verymuchtodo.todo.menu.MenuAlert;
import com.andraganoid.verymuchtodo.todo.menu.TodoBars;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.SphericalUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.Nullable;

public class ToDoViewModel extends AndroidViewModel {

    public ToDoViewModel(@NonNull Application application) {
        super(application);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        todo = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application);
        setTodoList(new ArrayList<TodoList>());
        setMessageList(new ArrayList<Message>());
        setMyself();
        setTodoBars("", "");
        setAlerts("all", false);
    }


    protected SharedPreferences prefs;
    private FirebaseFirestore todo;

    private MutableLiveData<ArrayList<User>> userList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TodoList>> todoList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Message>> messageList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ToDoLocation>> locationList = new MutableLiveData<>();

    private void setMyself() {
        User user = new User(prefs.getString("PREFS_ID", ""),
                prefs.getString("PREFS_NAME", ""),
                prefs.getString("PREFS_EMAIL", ""));
        mUser = user;

        if (!prefs.getBoolean("PREFS_IS_USER_REGISTRED", false)) {
            prefs.edit().putBoolean("PREFS_IS_USER_REGISTRED", true).apply();

            // toDoViewModel.addDocument.setValue(new Document(mUser));
            addDocument(new Document(user));

        }
    }


    public void addDocument(Document document) {
        todo.collection(document.getCollection())
                .document(document.getDocumentName())
                .set(document.getMap());
    }

    public void deleteDocument(Document document) {
        todo.collection(document.getCollection())
                .document(document.getDocumentName())
                .delete();
    }

    public void updateDocument(Document document) {
        todo.collection(document.getCollection())
                .document(document.getDocumentName())
                .update(document.getMap());
    }

    //  public ObservableField<User> mUser = new ObservableField<>();
    public User mUser;
    public Location mLocation = new Location("");
    // public ObservableField<Location> mLocation = new ObservableField<>();
    // public MutableLiveData<Document> addDocument = new MutableLiveData<>();

    // public MutableLiveData<Document> deleteDocument = new MutableLiveData<>();

    void setTodoList(ArrayList<TodoList> todoLists) {
        todoList.setValue(todoLists);
    }

    public LiveData<ArrayList<TodoList>> getTodoList() {
        return todoList;
    }

    void setMessageList(ArrayList<Message> msgList) {
        messageList.setValue(msgList);
    }

    public LiveData<ArrayList<Message>> getMessageList() {
        return messageList;
    }

    void setUserList(ArrayList<User> uList) {
        userList.setValue(uList);
    }

    public LiveData<ArrayList<User>> getUserList() {
        return userList;
    }

    void setLocationList(ArrayList<ToDoLocation> lList) {
        locationList.setValue(lList);
    }

    public LiveData<ArrayList<ToDoLocation>> getLocationList() {
        return locationList;
    }

    public TodoList currentToDoList;
    public TodoItem currentToDoItem;

    public MutableLiveData<TodoBars> todoBars = new MutableLiveData<>();

    public void setTodoBars(String title, String subtitle) {
        todoBars.setValue(new TodoBars(title, subtitle));
    }


    public MutableLiveData<MenuAlert> menuAlert = new MutableLiveData<>();

    public void setAlerts(String alertName, boolean alert) {
        MenuAlert ma = menuAlert.getValue();
        switch (alertName) {

            case "all":
                ma = new MenuAlert();
                break;

            case "list":
                ma.setListAlert(alert);
                //  ma.setListAlert(!ma.isListAlert());
                break;
            case "msg":
                ma.setMessageAlert(alert);
                break;
//            case "mUser":
//                ma.setUserAlert(alert);
//                break;
//            case "map":
//                ma.setMapAlert(alert);
//                break;
        }

        menuAlert.setValue(ma);


        Log.d("ALERT SET", String.valueOf(System.currentTimeMillis()) + alertName);

    }


    public Object clone(Object original, Object cloned) {
        for (Field field : original.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                field.set(cloned, field.get(original));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return cloned;
    }

    private StringBuilder sb = new StringBuilder();
    private Calendar cal = Calendar.getInstance();

    public String getLastEdit(String name, Long timestamp) {
        sb.setLength(0);
        sb.append(name)
                .append("@")
                .append(getFormattedDate(timestamp));
        return sb.toString();
    }

    public String getFormattedDate(Long timestamp) {
        if (timestamp != null) {
            cal.setTimeInMillis(timestamp);
            return DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();
        } else {
            return "";
        }
    }


    public void setFirebaseListeners() {

        todo.collection(Document.COLLECTION_TODO_LISTS).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    ArrayList<TodoList> tList = new ArrayList<>();
                    for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                        tList.add(qs.toObject(TodoList.class));
                    }

                    if (tList.size() > 0) {
                        Collections.sort(tList, new Comparator<TodoList>() {
                            @Override
                            public int compare(final TodoList object1, final TodoList object2) {
                                return String.valueOf(object2.getTimestamp()).compareTo(String.valueOf(object1.getTimestamp()));
                            }
                        });
                    }


                    long last = prefs.getLong("PREFS_LAST_TODO_LIST", 0);
                    boolean alert = false;
                    for (TodoList tl : tList) {
                        if (tl.getTimestamp()>last  && !mUser.getId().equals(tl.getUser().getId())) {
                            alert = true;
                            break;
                        }
                    }

                    if (alert) {
                        setAlerts("list", true);
                    }

                    prefs.edit().putLong("PREFS_LAST_TODO_LIST", tList.get(0).getTimestamp()).apply();
                    setTodoList(tList);

                }
            }
        });

        todo.collection(Document.COLLECTION_MESSAGES).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    ArrayList<Message> mList = new ArrayList<>();
                    for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                        mList.add(qs.toObject(Message.class));
                    }

                    if (mList.size() > 0) {
                        Collections.sort(mList, new Comparator<Message>() {
                            @Override
                            public int compare(final Message object1, final Message object2) {
                                return String.valueOf(object2.getTimestamp()).compareTo(String.valueOf(object1.getTimestamp()));
                            }
                        });
                    }



                    long last = prefs.getLong("PREFS_LAST_MESSAGE", 0);
                    boolean alert = false;
                    for (Message ml : mList) {
                        if (ml.getTimestamp()>last && !mUser.getId().equals(ml.getUser().getId())) {
                            alert = true;
                          break;
                        }
                    }


                    if (alert) {
                        setAlerts("msg", true);
                    }


                    prefs.edit().putLong("PREFS_LAST_MESSAGE", mList.get(0).getTimestamp()).apply();
                    setMessageList(mList);



                }
            }
        });


        todo.collection(Document.COLLECTION_USERS).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    ArrayList<User> uList = new ArrayList<>();
                    for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                        uList.add(qs.toObject(User.class));
                    }

                    if (uList.size() > 0) {
                        Collections.sort(uList, new Comparator<User>() {
                            @Override
                            public int compare(final User object1, final User object2) {
                                return String.valueOf(object1.getName()).compareTo(String.valueOf(object2.getName()));
                            }
                        });
                    }
                    setUserList(uList);
                }
            }
        });

        todo.collection(Document.COLLECTION_LOCATION).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    ArrayList<ToDoLocation> lList = new ArrayList<>();
                    for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                        lList.add(qs.toObject(ToDoLocation.class));
                    }

                    setLocationList(lList);

                }
            }
        });

    }

    public FusedLocationProviderClient fusedLocationClient;

//    public LocationHandler(@NonNull Application application) {
//        super(application);
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application);
//    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        // Toast.makeText(getApplication(), "GET LOCATION", Toast.LENGTH_SHORT).show();
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> saveCurrentLocation(location));

    }

    void saveCurrentLocation(Location location) {
        // Toast.makeText(getApplication(), String.valueOf(location.getTime()), Toast.LENGTH_SHORT).show();
        LatLng lastSavedLocation = new LatLng(prefs.getFloat("PREFS_LATITUDE",
                0), prefs.getFloat("PREFS_LONGITUDE", 0));
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mLocation = location;


//        Toast.makeText(getApplication(), String.valueOf(mLocation.getLatitude()), Toast.LENGTH_SHORT).show();
        if (lastSavedLocation.latitude == 0 && lastSavedLocation.longitude == 0) {
            addDocument(new Document(new ToDoLocation(mUser, location)));

        } else {
            if (SphericalUtil.computeDistanceBetween(lastSavedLocation, currentLocation) > 100) {
                updateDocument(new Document(new ToDoLocation(mUser, location)));
                prefs.edit()
                        .putFloat("PREFS_LATITUDE", (float) currentLocation.latitude)
                        .putFloat("PREFS_LONGITUDE", (float) currentLocation.longitude)
                        .apply();
                // mLocation = location;

            }
        }
        // Toast.makeText(getApplication(), String.valueOf(mLocation.getLatitude()), Toast.LENGTH_SHORT).show();
    }


}

