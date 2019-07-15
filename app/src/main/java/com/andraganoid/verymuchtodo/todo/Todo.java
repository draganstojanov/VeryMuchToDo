package com.andraganoid.verymuchtodo.todo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.MainActivity;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ActivityTodoBinding;
import com.andraganoid.verymuchtodo.todo.item.ItemFragment;
import com.andraganoid.verymuchtodo.todo.itemedit.ItemEditFragment;
import com.andraganoid.verymuchtodo.todo.list.ListFragment;
import com.andraganoid.verymuchtodo.todo.listedit.ListEditFragment;
import com.andraganoid.verymuchtodo.todo.map.MapFragment;
import com.andraganoid.verymuchtodo.todo.menu.MenuAlert;
import com.andraganoid.verymuchtodo.todo.menu.MenuClicker;
import com.andraganoid.verymuchtodo.todo.menu.TodoBars;
import com.andraganoid.verymuchtodo.todo.message.MessageFragment;
import com.google.firebase.auth.FirebaseAuth;


public class Todo extends AppCompatActivity implements MenuClicker {

    ToDoViewModel toDoViewModel;
    private SharedPreferences prefs;
    //  private FirebaseFirestore todo;
    public ActivityTodoBinding binding;

    public final Fragment LIST_FRAGMENT = new ListFragment();
    public final Fragment LIST_EDIT_FRAGMENT = new ListEditFragment();
    public final Fragment ITEM_FRAGMENT = new ItemFragment();
    public final Fragment ITEM_EDIT_FRAGMENT = new ItemEditFragment();
    public final Fragment MESSAGE_FRAGMENT = new MessageFragment();
    // public final Fragment USER_FRAGMENT = new UserFragment();
    public final Fragment MAP_FRAGMENT = new MapFragment();

   // public static User myself;
   // public TodoList currentList;

    @Override
    protected void onResume() {
        super.onResume();
        navigateToFragment(LIST_FRAGMENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo);
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        //  todo = FirebaseFirestore.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        registerObservers();
//        toDoViewModel.setTodoList(new ArrayList<TodoList>());
//        toDoViewModel.setMyself();
//        toDoViewModel.setTodoBars(getString(R.string.app_name), "");
//        toDoViewModel.setAlerts("all", false);
        binding.setMenuAlert(toDoViewModel.menuAlert.getValue());
        binding.setClicker(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        toDoViewModel.setFirebaseListeners();

//        todo.collection(Document.COLLECTION_TODO_LISTS).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if (queryDocumentSnapshots != null) {
//                    toDoViewModel.parseToDoListCollection(queryDocumentSnapshots);
//                }
//            }
//        });
//
//        todo.collection(Document.COLLECTION_MESSAGES).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if (queryDocumentSnapshots != null) {
//                    toDoViewModel.parseMessageCollection(queryDocumentSnapshots);
//                }
//            }
//        });


    }


    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

//    private void setMyself() {
//        User mUser = new User(prefs.getString("PREFS_ID", ""),
//                prefs.getString("PREFS_NAME", ""),
//                prefs.getString("PREFS_EMAIL", ""));
//        toDoViewModel.mUser.set(mUser);
//
//        if (!prefs.getBoolean("PREFS_IS_USER_REGISTRED", false)) {
//            prefs.edit().putBoolean("PREFS_IS_USER_REGISTRED", true).apply();
//
//           // toDoViewModel.addDocument.setValue(new Document(mUser));
//            toDoViewModel.addDocument(new Document(mUser));
//
//        }
//    }

    private void registerObservers() {
//        Log.d("REGISTER", String.valueOf(System.currentTimeMillis()));
//        toDoViewModel.getAddDocument().observe(this, new Observer<Document>() {
//            @Override
//            public void onChanged(Document document) {
//                todo.collection(document.getCollection())
//                        .document(document.getDocumentName())
//                        .set(document.getMap());
//            }
//        });
//
//        toDoViewModel.getDeleteDocument().observe(this, new Observer<Document>() {
//            @Override
//            public void onChanged(Document document) {
//                todo.collection(document.getCollection())
//                        .document(document.getDocumentName())
//                        .delete();
//            }
//        });

        toDoViewModel.todoBars.observe(this, new Observer<TodoBars>() {
            @Override
            public void onChanged(TodoBars todoBars) {
                binding.todoToolbar.setTitle(todoBars.getTitle());
                binding.todoToolbar.setSubtitle(todoBars.getSubtitle());
            }
        });

        toDoViewModel.menuAlert.observe(this, new Observer<MenuAlert>() {
            @Override
            public void onChanged(MenuAlert menuAlert) {
                //    Toast.makeText(Todo.this, "ALERT", Toast.LENGTH_SHORT).show();
                Log.d("ALERT", String.valueOf(System.currentTimeMillis()) + String.valueOf(menuAlert.isMessageAlert()));
                binding.invalidateAll();
            }
        });
    }


    public void navigateToFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.todo_fragment, fragment)
                    .commit();
        }
    }


    @Override
    public void onListItemClicked() {
        navigateToFragment(LIST_FRAGMENT);
    }

    @Override
    public void onMessageItemClicked() {
        // Toast.makeText(this, "MESSAGES", Toast.LENGTH_SHORT).show();
        navigateToFragment(MESSAGE_FRAGMENT);
    }

    @Override
    public void onUserItemClicked() {
        Toast.makeText(this, "USERS", Toast.LENGTH_SHORT).show();
        // navigateToFragment(USER_FRAGMENT);
    }

    @Override
    public void onMapItemClicked() {
        navigateToFragment(MAP_FRAGMENT);
    }

    @Override
    public void onLogoutItemClicked() {
        prefs.edit().putString("PREFS_ID", "")
                .putString("PREFS_NAME", "")
                .putString("PREFS_EMAIL", "")
                .putBoolean("PREFS_IS_USER_REGISTRED", false)
                .apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

}


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
//                    if (Long.parseLong(t.getTimestamp()) > lastList) {
//                        newLists++;
//                    }
//
//                }
//                if (todoList.size() > 0) {
//                    Collections.sort(todoList, new Comparator<TodoList>() {
//                        @Override
//                        public int compare(final TodoList object1, final TodoList object2) {
//                            return object2.getTimestamp().compareTo(object1.getTimestamp());
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


//    @Override
//    public void listChoosed(TodoList tl) {
//        currentList = tl;
//        //  navigateToFragment(ITEM_FRAGMENT);
//    }

//    @Override
//    public void changedCompletion(int position) {
//        currentList.getTodoItemList().get(position).setCompleted(!currentList.getTodoItemList().get(position).isCompleted());
//     //   saveList(currentList);
//    }