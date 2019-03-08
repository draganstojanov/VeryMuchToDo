package com.andraganoid.verymuchtodo;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.andraganoid.verymuchtodo.Model.Message;
import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.Model.User;
import com.andraganoid.verymuchtodo.Views.ListsFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Todo extends AppCompatActivity {


    public static final String COLLECTION_TODOS = "colToDos";
    public static final String COLLECTION_MESSAGES = "colMessages";
    public static final String COLLECTION_USERS = "colUsers";

    private final int MAIN_MENU_LISTS = 0;
    private final int MAIN_MENU_MSG = 1;
    private final int MAIN_MENU_USERS = 2;
    private final int MAIN_MENU_LIOGOUT = 3;

    private final Fragment listsFragment = new ListsFragment();
    //  private final Fragment messagesFragment=new MessagesFragment();
    //  private final Fragment userFragment=new UserFragment();

    private FirebaseFirestore todo;
    private CollectionReference saveLists;

    private List <User> userList = new ArrayList <>();
    public List <TodoList> todoList = new ArrayList <>();
    private List <Message> messagesList = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);
        todo = FirebaseFirestore.getInstance();

        saveLists = todo.collection(COLLECTION_TODOS);

        Map <String, Object> data1 = new HashMap <>();
        data1.put("name", "Maxi");
        data1.put("shortDescription", "Kupi nesto u maxiju");
        saveLists.document("Maxi").set(data1);

        Map <String, Object> data2 = new HashMap <>();
        data2.put("name", "Roda");
        data2.put("shortDescription", "Kupi nesto najlepsije u rodi");
        saveLists.document("Roda").set(data2);

        Map <String, Object> data3 = new HashMap <>();
        data3.put("name", "Idea");
        data3.put("shortDescription", "Kupi nesto u idei");
        saveLists.document("Idea").set(data3);

        Map <String, Object> data4 = new HashMap <>();
        data4.put("name", "Aman");
        data4.put("shortDescription", "Kupi nesto u amanu");
        saveLists.document("Aman").set(data4);

        Map <String, Object> data5 = new HashMap <>();
        data5.put("name", "Aroma");
        data5.put("shortDescription", "Kupi nesto u aromi");
        saveLists.document("Aroma").set(data5);

        //   setFragment(MAIN_MENU_LISTS);

//        todoList.add(new TodoList("Maxi", "Kupi u maxiju"));
//        todoList.add(new TodoList("Roda", "Kupi u rodi"));
//        todoList.add(new TodoList("Idea", "Kupi u idei"));

        setFragment(listsFragment);

        BottomNavigationView bottomMain = findViewById(R.id.main_bottom_bar);
        bottomMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.main_lists:
                        break;
                    case R.id.main_msg:
                        break;
                    case R.id.main_users:
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

        todo.collection((COLLECTION_TODOS)).addSnapshotListener(this, new EventListener <QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                todoList.clear();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    todoList.add(qs.toObject(TodoList.class));
                    //   setFragment(MAIN_MENU_LISTS);
                    Log.d("COLLECTION_TODOS", String.valueOf(todoList.size()));
                }
                ListsFragment listsFragmentInstance = (ListsFragment) getSupportFragmentManager().findFragmentById(R.id.todo_fragment);
                if (listsFragmentInstance != null) {
                    listsFragmentInstance.refreshLists();
                }

            }
        });

//        todo.collection((COLLECTION_MESSAGES)).addSnapshotListener(this, new EventListener <QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                messagesList.clear();
//                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
//                    messagesList.add(qs.toObject(Message.class));
//                    Log.d("COLLECTION_MESSAGES", String.valueOf(messagesList.size()));
//                }
//            }
//        });
//
//        todo.collection((MainActivity.COLLECTION_USERS)).addSnapshotListener(this, new EventListener <QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                userList.clear();
//                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
//                    userList.add(qs.toObject(User.class));
//                }
//            }
//        });
    }


    public void deleteDocument(final String collection, String document) {

        todo.collection(collection).document(document)
                .delete()
                .addOnSuccessListener(new OnSuccessListener <Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DELETE", "DocumentSnapshot successfully deleted!");

                        switch (collection) {
                            case COLLECTION_TODOS:
                                ListsFragment listsFragmentInstance = (ListsFragment) getSupportFragmentManager().findFragmentById(R.id.todo_fragment);
                                if (listsFragmentInstance != null) {
                                    listsFragmentInstance.refreshLists();
                                }
                                break;
                            case COLLECTION_MESSAGES:
                                break;
                        }

                        ListsFragment listsFragmentInstance = (ListsFragment) getSupportFragmentManager().findFragmentById(R.id.todo_fragment);
                        if (listsFragmentInstance != null) {
                            listsFragmentInstance.refreshLists();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DELETE", "Error deleting document", e);
                    }
                });


    }


    private void setFragment(Fragment fragment) {

        // Fragment fragment = null;
//        switch (frag) {
//
//            case MAIN_MENU_LISTS:
//                fragment = new ListsFragment();
//                break;
//            case MAIN_MENU_MSG:
//                break;
//            case MAIN_MENU_USERS:
//                break;
//        }

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
