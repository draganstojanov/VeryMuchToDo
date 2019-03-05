package com.andraganoid.verymuchtodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import com.andraganoid.verymuchtodo.Model.Message;
import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.Model.User;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.Nullable;

public class Todo extends AppCompatActivity {


    private final String COLLECTION_TODOS = "colToDos";
    private final String COLLECTION_MESSAGES = "colMessages";

    private FirebaseFirestore todo;
    private User myself;

    private List <User> userList = new ArrayList <>();
    private List <TodoList> todoList = new ArrayList <>();
    private List <Message> messagesList = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);

        todo = FirebaseFirestore.getInstance();
        // fetchAllUsersData();

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
                    Log.d("COLLECTION_TODOS", String.valueOf(todoList.size()));
                }


            }
        });

        todo.collection((COLLECTION_MESSAGES)).addSnapshotListener(this, new EventListener <QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                messagesList.clear();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    messagesList.add(qs.toObject(Message.class));
                    Log.d("COLLECTION_MESSAGES", String.valueOf(messagesList.size()));
                }


            }
        });

        todo.collection((MainActivity.COLLECTION_USERS)).addSnapshotListener(this, new EventListener <QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                userList.clear();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    userList.add(qs.toObject(User.class));
                }


            }
        });
    }

    //    private void fetchAllUsersData() {
//
//        todo.collection(COLLECTION_USERS)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            userList.clear();
//                            boolean me = false;
//                            for (QueryDocumentSnapshot userDoc : task.getResult()) {
//
//                                userList.add(userDoc.toObject(User.class));
//                                if (userDoc.getId().equals(myself.getId())) {
//                                    me = true;
//                                }
//
//                            }
//                            if (!me) {
//                                addUser(myself);
//                            }
//                        } else {
//                            addUser(myself);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }


}
