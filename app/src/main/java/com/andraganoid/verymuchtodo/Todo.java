package com.andraganoid.verymuchtodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import com.andraganoid.verymuchtodo.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Todo extends AppCompatActivity {

    private final String COLLECTION_USERS = "colUsers";
    private final String COLLECTION_TODOS = "colToDos";
    private final String COLLECTION_MESSAGES = "colMessages";

    private FirebaseFirestore todo;
    private User myself;

    private List <User> userList = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo);
        myself = new User(
                getIntent().getStringExtra("userId"),
                getIntent().getStringExtra("displayName"),
                getIntent().getStringExtra("email"));

        todo = FirebaseFirestore.getInstance();
        fetchAllUsersData();

    }

    private void fetchAllUsersData() {

        todo.collection(COLLECTION_USERS)
                .get()
                .addOnCompleteListener(new OnCompleteListener <QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task <QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            userList.clear();
                            boolean me = false;
                            for (QueryDocumentSnapshot userDoc : task.getResult()) {

                                userList.add(userDoc.toObject(User.class));
                                if (userDoc.getId().equals(myself.getId())) {
                                    me = true;
                                }

                            }
                            if (!me) {
                                addUser(myself);
                            }
                        } else {
                            addUser(myself);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private void addUser(User user) {

        Map <String, Object> addUser = new HashMap <>();

        addUser.put("id", user.getId());
        addUser.put("name", user.getName());
        addUser.put("email", user.getEmail());

        todo.collection(COLLECTION_USERS)
                .document(user.getName())
                .set(addUser)
                .addOnSuccessListener(new OnSuccessListener <Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //  Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //   Log.w(TAG, "Error writing document", e);
                    }
                });


    }


}
