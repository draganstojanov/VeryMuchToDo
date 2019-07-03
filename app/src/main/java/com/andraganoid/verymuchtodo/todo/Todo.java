package com.andraganoid.verymuchtodo.todo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.MainActivity;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.VeryOnItemClickListener;
import com.andraganoid.verymuchtodo.Views.ItemFragment;
import com.andraganoid.verymuchtodo.Views.ListFragment;
import com.andraganoid.verymuchtodo.Views.MessageFragment;
import com.andraganoid.verymuchtodo.Views.UserFragment;
import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.model.User;
import com.andraganoid.verymuchtodo.todo.map.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Todo extends AppCompatActivity implements VeryOnItemClickListener {

    ToDoViewModel toDoViewModel;
    private SharedPreferences prefs;

    public static final String COLLECTION_TODOS = "colToDos";
    public static final String COLLECTION_MESSAGES = "colMessages";
    final String COLLECTION_USERS = "colUsers";

    //    private final Fragment listsFragment = new ListFragment();
//    private final Fragment itemFragment = new ItemFragment();
//    private final Fragment msgFragment = new MessageFragment();
//    private final Fragment userFragment = new UserFragment();

    public static final Fragment LIST_FRAGMENT = new ListFragment();
    public static final Fragment ITEM_FRAGMENT = new ItemFragment();
    public static final Fragment MESSAGE_FRAGMENT = new MessageFragment();
    public static final Fragment USER_FRAGMENT = new UserFragment();
    public static final Fragment MAP_FRAGMENT = new MapFragment();

    //  private FirebaseFirestore todo;
    //  public static User myself;
    public TodoList currentList;

    public BottomNavigationView bottomMain;

    private Map<String, Object> documentData = new HashMap<>();

//    public List<User> userList = new ArrayList<>();
//    public List<TodoList> todoList = new ArrayList<>();
//    public List<Message> messagesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        toDoViewModel.todo = FirebaseFirestore.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setMyself();

        setFragment(LIST_FRAGMENT);

        bottomMain = findViewById(R.id.main_bottom_bar);
        bottomMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                findViewById(menuItem.getItemId()).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                switch (menuItem.getItemId()) {

                    case R.id.main_lists:
                        setFragment(LIST_FRAGMENT);
                        break;
                    case R.id.main_msg:
                        setFragment(MESSAGE_FRAGMENT);
                        break;
                    case R.id.main_users:
                        setFragment(USER_FRAGMENT);
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

//        todo.collection(COLLECTION_MESSAGES).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                long lastMsg = prefs.getLong("lastMsg", 0L);
//                int newMsg = 0;
//                messagesList.clear();
//                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
//                    Message mes = qs.toObject(Message.class);
//                    messagesList.add(mes);
//                    if (mes.getTimestamp() > lastMsg) {
//                        newMsg++;
//                    }
//                }
//
//
//                if (messagesList.size() > 0) {
//                    Collections.sort(messagesList, new Comparator<Message>() {
//                        @Override
//                        public int compare(final Message object1, final Message object2) {
//                            return object1.getTimestampAsString().compareTo(object2.getTimestampAsString());
//                        }
//                    });
//                }
//
//                if (newMsg > 0) {
//                    prefs.edit().putLong("lastMsg", System.currentTimeMillis()).apply();
//                    if (!messagesList.get(messagesList.size() - 1).getId().equals(myself.getId())) {
//                        findViewById(R.id.main_msg).setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                    }
//
//                }
//
//                for (Message m : messagesList) {
//
//                }
//
//                goFragment();
//
//            }
//        });
//
//
//        todo.collection(COLLECTION_TODOS).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                long lastList = prefs.getLong("lastList", 0L);
//                int newLists = 0;
//                todoList.clear();
//                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
//                    TodoList t = qs.toObject(TodoList.class);
//                    if (currentList != null) {
//                        if (t.getTitle().equals(currentList.getTitle())) {
//                            currentList = t;
//                        }
//                    }
//
//                    todoList.add(t);
//                    System.out.println("TODO: " + t.getLastEdit());
//                    if (Long.parseLong(t.getLastEditTimestamp()) > lastList) {
//                        newLists++;
//                    }
//
//                }
//                if (todoList.size() > 0) {
//                    Collections.sort(todoList, new Comparator<TodoList>() {
//                        @Override
//                        public int compare(final TodoList object1, final TodoList object2) {
//                            return object2.getLastEditTimestamp().compareTo(object1.getLastEditTimestamp());
//                        }
//                    });
//                }
//
//                if (newLists > 0) {
//                    prefs.edit().putLong("lastList", System.currentTimeMillis()).apply();
//
//                    if (!todoList.get(0).getLastEditId().equals(myself.getId())) {
//                        findViewById(R.id.main_lists).setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                    }
//
//                }
//
//                for (TodoList m : todoList) {
//                    System.out.println("TODOLIST: " + m.getTitle());
//
//
//                }
//                goFragment();
//
//            }
//        });
//
//
//        todo.collection((COLLECTION_USERS)).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                userList.clear();
//                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
//                    userList.add(qs.toObject(User.class));
//                }
//            }
//        });
    }


    private void goFragment() {
        Fragment fragmentInstance = getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        if (fragmentInstance != null) {
            if (fragmentInstance == LIST_FRAGMENT) {
                ((ListFragment) fragmentInstance).refreshLists();
            } else if (fragmentInstance == ITEM_FRAGMENT) {
                ((ItemFragment) fragmentInstance).refreshItems();
            } else if (fragmentInstance == MESSAGE_FRAGMENT) {
                ((MessageFragment) fragmentInstance).refreshMsg();
            }
        }

    }


    public void deleteDocument(final String collection, String document) {
//        todo.collection(collection)
//                .document(document)
//                .delete();
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
        setFragment(ITEM_FRAGMENT);
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


    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    private void setMyself() {
        User user = new User(prefs.getString("PREFS_ID", ""),
                prefs.getString("PREFS_NAME", ""),
                prefs.getString("PREFS_EMAIL", ""));
        toDoViewModel.user.set(user);

        if (prefs.getBoolean("PREFS_IS_USER_REGISTRED", false)) {
            prefs.edit().putBoolean("PREFS_IS_USER_REGISTRED", true).apply();

            documentData.clear();
            documentData.put("id", user.getId());
            documentData.put("name", user.getName());
            documentData.put("email", user.getEmail());
            documentData.put("location", user.getLocation());
            documentData.put("locationTimestamp", user.getLocationTimestamp());

            addDocument(COLLECTION_USERS, user.getId(), documentData);
        }
    }

    public void addDocument(String collection, String document, Map map) {
        toDoViewModel.todo.collection(collection)
                .document(document)
                .set(map);
    }


}