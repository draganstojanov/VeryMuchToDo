package com.andraganoid.verymuchtodo.todo;

import android.app.Application;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;

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
import com.andraganoid.verymuchtodo.todo.location.LocationHandler;
import com.andraganoid.verymuchtodo.todo.location.LocationHandlerCallback;
import com.andraganoid.verymuchtodo.todo.menu.MenuAlert;
import com.andraganoid.verymuchtodo.todo.menu.TodoBars;
import com.andraganoid.verymuchtodo.todo.repository.FirebaseCallback;
import com.andraganoid.verymuchtodo.todo.repository.FirebaseRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class ToDoViewModel extends AndroidViewModel implements FirebaseCallback, LocationHandlerCallback {

    private SharedPreferences prefs;
    public User mUser;
    public Location mLocation = new Location("");
    public TodoList currentToDoList;
    public TodoItem currentToDoItem;
    public FirebaseRepository fbRepo;
    public LocationHandler locationHandler;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        fbRepo = new FirebaseRepository(this);
      //  locationHandler = new LocationHandler(application, this);
        setTodoList(new ArrayList<TodoList>());
        setMessageList(new ArrayList<Message>());
        setMyself();
        setTodoBars("", false);
        setAlerts("all", false);
    }

    private MutableLiveData<ArrayList<User>> userList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TodoList>> todoList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Message>> messageList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ToDoLocation>> locationList = new MutableLiveData<>();

    private void setTodoList(ArrayList<TodoList> todoLists) {
        todoList.setValue(todoLists);
    }

    public LiveData<ArrayList<TodoList>> getTodoList() {
        return todoList;
    }

    private void setMessageList(ArrayList<Message> msgList) {
        messageList.setValue(msgList);
    }

    public LiveData<ArrayList<Message>> getMessageList() {
        return messageList;
    }

    private void setUserList(ArrayList<User> uList) {
        userList.setValue(uList);
    }

    public LiveData<ArrayList<User>> getUserList() {
        return userList;
    }

    private void setLocationList(ArrayList<ToDoLocation> lList) {
        locationList.setValue(lList);
    }

    public LiveData<ArrayList<ToDoLocation>> getLocationList() {
        return locationList;
    }

    MutableLiveData<TodoBars> todoBars = new MutableLiveData<>();

    public void setTodoBars(String title, boolean emergency) {
        todoBars.setValue(new TodoBars(title, emergency));
    }

    MutableLiveData<MenuAlert> menuAlert = new MutableLiveData<>();

    public void setAlerts(String alertName, boolean alert) {
        MenuAlert ma = menuAlert.getValue();
        switch (alertName) {
            case "all":
                ma = new MenuAlert();
                break;
            case "list":
                ma.setListAlert(alert);
                break;
            case "msg":
                ma.setMessageAlert(alert);
                break;
        }
        menuAlert.setValue(ma);
    }

    private void setMyself() {
        User user = new User(prefs.getString("PREFS_ID", ""),
                prefs.getString("PREFS_NAME", ""),
                prefs.getString("PREFS_EMAIL", ""));
        mUser = user;
        if (!prefs.getBoolean("PREFS_IS_USER_REGISTRED", false)) {
            prefs.edit().putBoolean("PREFS_IS_USER_REGISTRED", true).apply();
            fbRepo.addDocument(new Document(user));
        }
    }

    public void addDocument(Document document) {
        fbRepo.addDocument(document);
    }

    public void deleteDocument(Document document) {
        fbRepo.deleteDocument(document);
    }

    public void updateDocument(Document document) {
        fbRepo.updateDocument(document);
    }

    @Override
    public void listsUpdated(ArrayList<TodoList> tList) {
        long last = prefs.getLong("PREFS_LAST_TODO_LIST", 0);
        boolean alert = false;
        for (TodoList tl : tList) {
            if (tl.getTimestamp() > last && !mUser.getId().equals(tl.getUser().getId())) {
                alert = true;
                break;
            }
        }
        if (alert) {
            setAlerts("list", true);
        }
        if (tList.size() > 0) {
            prefs.edit().putLong("PREFS_LAST_TODO_LIST", tList.get(0).getTimestamp()).apply();
        } else {
            prefs.edit().putLong("PREFS_LAST_TODO_LIST", System.currentTimeMillis()).apply();
        }
        setTodoList(tList);
    }

    @Override
    public void messagesUpdated(ArrayList<Message> mList) {
        long last = prefs.getLong("PREFS_LAST_MESSAGE", 0);
        boolean alert = false;
        for (Message ml : mList) {
            if (ml.getTimestamp() > last && !mUser.getId().equals(ml.getUser().getId())) {
                alert = true;
                break;
            }
        }
        if (alert) {
            setAlerts("msg", true);
        }
        if (mList.size() > 0) {
            prefs.edit().putLong("PREFS_LAST_MESSAGE", mList.get(0).getTimestamp()).apply();
        } else {
            prefs.edit().putLong("PREFS_LAST_MESSAGE", System.currentTimeMillis()).apply();
        }
        setMessageList(mList);
    }

    @Override
    public void usersUpdated(ArrayList<User> uList) {
        setUserList(uList);
    }

    @Override
    public void locationsUpdated(ArrayList<ToDoLocation> lList) {
        setLocationList(lList);
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

    void getCurrentLocation() {
   //     locationHandler.getCurrentLocation();
    }

    @Override
    public void saveCurrentLocation(Location location) {
//        if (location != null) {
//            LatLng lastSavedLocation = new LatLng(prefs.getFloat("PREFS_LATITUDE",
//                    0), prefs.getFloat("PREFS_LONGITUDE", 0));
//            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
//            mLocation = location;
//
//            if (lastSavedLocation.latitude == 0 && lastSavedLocation.longitude == 0) {
//                fbRepo.addDocument(new Document(new ToDoLocation(mUser, location)));
//
//            } else {
//                if (SphericalUtil.computeDistanceBetween(lastSavedLocation, currentLocation) > 50) {
//                    fbRepo.updateDocument(new Document(new ToDoLocation(mUser, location)));
//                    prefs.edit()
//                            .putFloat("PREFS_LATITUDE", (float) currentLocation.latitude)
//                            .putFloat("PREFS_LONGITUDE", (float) currentLocation.longitude)
//                            .apply();
//                }
//            }
//        } else {
//            mLocation = new Location("");
//        }
   }
}