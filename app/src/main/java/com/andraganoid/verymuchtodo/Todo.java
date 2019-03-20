package com.andraganoid.verymuchtodo;


import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.fragment.app.Fragment;


public class Todo extends AppCompatActivity implements VeryOnItemClickListener {

    private SharedPreferences prefs;

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

                findViewById(menuItem.getItemId()).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

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


    @Override
    protected void onStart() {
        super.onStart();

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


                if (messagesList.size() > 0) {
                    Collections.sort(messagesList, new Comparator <Message>() {
                        @Override
                        public int compare(final Message object1, final Message object2) {
                            return object1.getTimestampAsString().compareTo(object2.getTimestampAsString());
                        }
                    });
                }

                if (newMsg > 0) {
                    prefs.edit().putLong("lastMsg", System.currentTimeMillis()).apply();
                    if (!messagesList.get(messagesList.size() - 1).getId().equals(myself.getId())) {
                        findViewById(R.id.main_msg).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }

                }

                for (Message m:messagesList){

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
                    System.out.println("TODO: "+t.getLastEdit());
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

                    if (!todoList.get(0).getLastEditId().equals(myself.getId())) {
                        findViewById(R.id.main_lists).setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }

                }

                for (TodoList m:todoList){
                    System.out.println("TODOLIST: "+m.getTitle());


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
                }
            }
        });
    }


    private void goFragment() {
        Fragment fragmentInstance = getSupportFragmentManager().findFragmentById(R.id.todo_fragment);

        if (fragmentInstance != null) {
            if (fragmentInstance == listsFragment) {
                ((ListFragment) fragmentInstance).refreshLists();
            } else if (fragmentInstance == itemFragment) {
                ((ItemFragment) fragmentInstance).refreshItems();
            } else if (fragmentInstance == msgFragment) {
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
        documentData.put("lastEditId", todoList.getLastEditId());

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
