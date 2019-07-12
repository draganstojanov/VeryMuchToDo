package com.andraganoid.verymuchtodo.todo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.MainActivity;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.VeryOnItemClickListener;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.model.User;
import com.andraganoid.verymuchtodo.todo.item.ItemFragment;
import com.andraganoid.verymuchtodo.todo.itemedit.ItemEditFragment;
import com.andraganoid.verymuchtodo.todo.list.ListFragment;
import com.andraganoid.verymuchtodo.todo.listedit.ListEditFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;


public class Todo extends AppCompatActivity {

    ToDoViewModel toDoViewModel;
    private SharedPreferences prefs;
    private FirebaseFirestore todo;
    // Repository repo;

    public final Fragment LIST_FRAGMENT = new ListFragment();
    public final Fragment LIST_EDIT_FRAGMENT = new ListEditFragment();
    public final Fragment ITEM_FRAGMENT = new ItemFragment();
    public final Fragment ITEM_EDIT_FRAGMENT = new ItemEditFragment();




    public static User myself;
    public TodoList currentList;

    public BottomNavigationView bottomMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        todo = FirebaseFirestore.getInstance();
        //  repo = Repository.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        registerObservers();
        toDoViewModel.setTodoList(new ArrayList<TodoList>());
        setMyself();
        navigateToFragment(LIST_FRAGMENT);


        bottomMain = findViewById(R.id.main_bottom_bar);
        bottomMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                findViewById(menuItem.getItemId()).setBackgroundColor(getResources().getColor(R.color.colorPrimary));

//                switch (menuItem.getItemId()) {
//
//                    case R.id.main_lists:
//                        navigateToFragment(LIST_FRAGMENT);
//                        break;
//                    case R.id.main_msg:
//                        navigateToFragment(MESSAGE_FRAGMENT);
//                        break;
//                    case R.id.main_users:
//                        navigateToFragment(USER_FRAGMENT);
//                        break;
//                    case R.id.main_logout:
//                        logout();
//                        break;
//
//                }
                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        todo.collection(Document.COLLECTION_TODO_LISTS).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                toDoViewModel.parseToDoListCollection(queryDocumentSnapshots);
            }
        });


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
    }


    private void goFragment() {
        Fragment fragmentInstance = getSupportFragmentManager().findFragmentById(R.id.main_fragment);

//        if (fragmentInstance != null) {
//            if (fragmentInstance == LIST_FRAGMENT) {
//                //((ListFragment) fragmentInstance).refreshLists();
//            } else if (fragmentInstance == ITEM_FRAGMENT) {
//                ((ItemFragment) fragmentInstance).refreshItems();
//            } else if (fragmentInstance == MESSAGE_FRAGMENT) {
//                ((MessageFragment) fragmentInstance).refreshMsg();
//            }
        //  }

    }



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


    public void sendMessage(Message message) {
//        documentData.clear();
//        documentData.put("text", message.getText());
//        documentData.put("timestamp", message.getTimestamp());
//        documentData.put("from", message.getFrom());
//        documentData.put("title", message.getTitle());
//        documentData.put("id", message.getId());

        // addDocument(COLLECTION_MESSAGES, message.getTitle(), documentData);
    }


    public void navigateToFragment(Fragment fragment) {

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

        if (!prefs.getBoolean("PREFS_IS_USER_REGISTRED", false)) {
            prefs.edit().putBoolean("PREFS_IS_USER_REGISTRED", true).apply();

            toDoViewModel.addDocument.setValue(new Document(user));

        }
    }

    private void registerObservers() {
        Log.d("REGISTER",String.valueOf(System.currentTimeMillis()));
        toDoViewModel.getAddDocument().observe(this, new Observer<Document>() {
            @Override
            public void onChanged(Document document) {

               // addDocument(document);

                todo.collection(document.getCollection())
                        .document(document.getDocumentName())
                        .set(document.getMap());
           }
        });

        toDoViewModel.getDeleteDocument().observe(this, new Observer<Document>() {
            @Override
            public void onChanged(Document document) {

              //  deleteDocument(document);
                todo.collection(document.getCollection())
                        .document(document.getDocumentName())
                        .delete();
            }
        });
    }

//    public void addDocument(Document document) {
//        todo.collection(document.getCollection())
//                .document(document.getDocumentName())
//                .set(document.getMap());
//    }
//
//    public void deleteDocument(Document document) {
//
//        todo.collection(document.getCollection())
//                .document(document.getDocumentName())
//                .delete();
//    }


}
