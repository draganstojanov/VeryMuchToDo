package com.andraganoid.verymuchtodo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.andraganoid.verymuchtodo.Model.Message;
import com.andraganoid.verymuchtodo.Model.TodoItem;
import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.Model.User;
import com.andraganoid.verymuchtodo.Views.ItemFragment;
import com.andraganoid.verymuchtodo.Views.ListFragment;
import com.andraganoid.verymuchtodo.Views.MsgFragment;
import com.andraganoid.verymuchtodo.Views.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;


public class Todo extends AppCompatActivity implements VeryOnItemClickListener {

    private SharedPreferences prefs;
    private final String CHANNEL_ID="myNotif";

    public static final String COLLECTION_TODOS = "colToDos";
    public static final String COLLECTION_MESSAGES = "colMessages";
    public static final String COLLECTION_USERS = "colUsers";

    private final Fragment listsFragment = new ListFragment();
    private final Fragment itemFragment = new ItemFragment();
    private final Fragment msgFragment = new MsgFragment();
    private final Fragment userFragment = new UserFragment();

    private FirebaseFirestore todo;
    public static User myself;
    public TodoList currentList;

    public BottomNavigationView bottomMain;

    private Map <String, Object> documentData = new HashMap <>();

    public List <User> userList = new ArrayList <>();
    public List <TodoList> todoList = new ArrayList <>();
    public List <Message> messagesList = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);
        todo = FirebaseFirestore.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setMyself();

        setFragment(listsFragment);

        bottomMain = findViewById(R.id.main_bottom_bar);
        bottomMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.main_lists:
                        setFragment(listsFragment);
                        break;
                    case R.id.main_msg:
                        setFragment(msgFragment);
                        break;
                    case R.id.main_users:
                        setFragment(userFragment);
                        break;
                    case R.id.main_logout:
                        logout();
                        break;

                }
                return false;
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
                        CharSequence name = "name";
            String description = "description";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNotificationChannel();
        todo.collection(COLLECTION_MESSAGES).addSnapshotListener(this, new EventListener <QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                long lastMsg = prefs.getLong("lastMsg", 0L);
                int newMsg = 0;
                messagesList.clear();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    Message mes = qs.toObject(Message.class);
                    messagesList.add(mes);


                    if (mes.getTimestamp() > lastMsg) {
                        newMsg++;
                    }
                }

                if (newMsg > 0) {
                    prefs.edit().putLong("lastMsg", System.currentTimeMillis()).apply();
                    findViewById(R.id.main_msg).setBackgroundColor(getResources().getColor(R.color.colorAccent));


                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("New message")
                            .setContentText("You have some new messages")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                    builder.setAutoCancel(true);
                    builder.setLights(Color.BLUE, 500, 500);
                    long[] pattern = {500,500,500,500,500,500,500,500,500};
                    builder.setVibrate(pattern);
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    builder.setSound(alarmSound);
                    builder.setStyle(new NotificationCompat.InboxStyle());


                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());





                }
                goFragment();

            }
        });


        todo.collection(COLLECTION_TODOS).addSnapshotListener(this, new EventListener <QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                long lastList = prefs.getLong("lastList", 0L);
                int newLists = 0;
                todoList.clear();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    TodoList t = qs.toObject(TodoList.class);
                    if (currentList != null) {
                        if (t.getTitle().equals(currentList.getTitle())) {
                            currentList = t;
                        }
                    }

                    todoList.add(t);
                    if (Long.parseLong(t.getLastEditTimestamp()) > lastList) {
                        newLists++;
                    }

                }
                if (todoList.size() > 0) {
                    Collections.sort(todoList, new Comparator <TodoList>() {
                        @Override
                        public int compare(final TodoList object1, final TodoList object2) {
                            return object2.getLastEditTimestamp().compareTo(object1.getLastEditTimestamp());
                        }
                    });
                }

                if (newLists > 0) {
                    prefs.edit().putLong("lastList", System.currentTimeMillis()).apply();
                    findViewById(R.id.main_lists).setBackgroundColor(getResources().getColor(R.color.colorAccent));

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("New ToDo's")
                            .setContentText("You have some new ToDo's to do!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                    builder.setAutoCancel(true);
                    builder.setLights(Color.RED, 500, 500);
                    long[] pattern = {500,500,500,500,500,500,500,500,500};
                    builder.setVibrate(pattern);
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    builder.setSound(alarmSound);
                    builder.setStyle(new NotificationCompat.InboxStyle());


                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(2, builder.build());

                }
                goFragment();

            }
        });


        todo.collection((COLLECTION_USERS)).addSnapshotListener(this, new EventListener <QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                userList.clear();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    userList.add(qs.toObject(User.class));
                    //   findViewById(R.id.main_users).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }
        });
}

    private void goFragment() {
        Fragment fragmentInstance = getSupportFragmentManager().findFragmentById(R.id.todo_fragment);

        if (fragmentInstance != null) {
            if (fragmentInstance == listsFragment) {
                System.out.println("WHAT FRAG LISTS");
                ((ListFragment) fragmentInstance).refreshLists();
            } else if (fragmentInstance == itemFragment) {
                System.out.println("WHAT FRAG ITEM");
                ((ItemFragment) fragmentInstance).refreshItems();

            } else if (fragmentInstance == msgFragment) {
                System.out.println("WHAT FRAG MSG");
                ((MsgFragment) fragmentInstance).refreshMsg();
            }
        }

    }

    public void addDocument(String collection, String document, Map map) {
        todo.collection(collection)
                .document(document)
                .set(map);
    }


    public void deleteDocument(final String collection, String document) {
        todo.collection(collection)
                .document(document)
                .delete();
    }


    private void setMyself() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        myself = new User(prefs.getString("id", ""),
                prefs.getString("name", ""),
                prefs.getString("email", ""));
        if (prefs.getBoolean("register", false)) {
            prefs.edit().putBoolean("register", false).apply();

            documentData.clear();
            documentData.put("id", myself.getId());
            documentData.put("name", myself.getName());
            documentData.put("email", myself.getEmail());
            documentData.put("location", myself.getLocation());

            addDocument(COLLECTION_USERS, myself.getId(), documentData);

        }
    }

    public void saveList(TodoList todoList) {

        if (todoList.getTodoItemList().size() > 0) {
            boolean co = true;
            for (TodoItem ti : todoList.getTodoItemList()) {
                co = co && ti.isCompleted();
            }
            todoList.setCompleted(co);
        }

        documentData.clear();
        documentData.put("title", todoList.getTitle());
        documentData.put("shortDescription", todoList.getShortDescription());
        documentData.put("lastEdit", todoList.getLastEdit());
        documentData.put("lastEditTimestamp", todoList.getLastEditTimestamp());
        documentData.put("emergency", todoList.isEmergency());
        documentData.put("completed", todoList.isCompleted());
        documentData.put("todoItemList", todoList.getTodoItemList());

        addDocument(COLLECTION_TODOS, todoList.getTitle(), documentData);

        listChoosed(todoList);

    }

    @Override
    public void listChoosed(TodoList tl) {
        currentList = tl;
        setFragment(itemFragment);
    }

    @Override
    public void changedCompletion(int position) {
        currentList.getTodoItemList().get(position).setCompleted(!currentList.getTodoItemList().get(position).isCompleted());
        saveList(currentList);
    }


    public void sendMessage(Message message) {
        documentData.clear();
        documentData.put("text", message.getText());
        documentData.put("timestamp", message.getTimestamp());
        documentData.put("from", message.getFrom());
        documentData.put("title", message.getTitle());
        documentData.put("id", message.getId());

        addDocument(COLLECTION_MESSAGES, message.getTitle(), documentData);
    }


    private void setFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.todo_fragment, fragment)
                    .commit();
        }
    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        this.startActivity(new Intent(this, MainActivity.class));
        this.finishAffinity();
    }

    public void setTitle(String title, String subtitle) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setSubtitle(subtitle);
    }


}
