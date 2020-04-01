package com.andraganoid.verymuchtodo.J.todo.repository;


import com.andraganoid.verymuchtodo.J.model.Document;
import com.andraganoid.verymuchtodo.J.model.Message;
import com.andraganoid.verymuchtodo.J.model.ToDoLocation;
import com.andraganoid.verymuchtodo.J.model.TodoList;
import com.andraganoid.verymuchtodo.J.model.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseRepository {

    private FirebaseFirestore todo;
    private FirebaseCallback fbCallback;

    public FirebaseRepository(FirebaseCallback fbCallback) {
        todo = FirebaseFirestore.getInstance();
        this.fbCallback = fbCallback;
        setFirebaseListeners();
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

    private void setFirebaseListeners() {
        todo.collection(Document.COLLECTION_TODO_LISTS).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null) {
                ArrayList<TodoList> tList = new ArrayList<>();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    tList.add(qs.toObject(TodoList.class));
                }
                if (tList.size() > 0) {
                    Collections.sort(tList, (o1, o2) -> String.valueOf(o2.getTimestamp()).compareTo(String.valueOf(o1.getTimestamp())));
                }
                fbCallback.listsUpdated(tList);
            }
        });

        todo.collection(Document.COLLECTION_MESSAGES).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null) {
                ArrayList<Message> mList = new ArrayList<>();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    mList.add(qs.toObject(Message.class));
                }
                if (mList.size() > 0) {
                    Collections.sort(mList, (o1, o2) -> String.valueOf(o2.getTimestamp()).compareTo(String.valueOf(o1.getTimestamp())));
                }
                fbCallback.messagesUpdated(mList);
            }
        });


        todo.collection(Document.COLLECTION_USERS).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null) {
                ArrayList<User> uList = new ArrayList<>();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    uList.add(qs.toObject(User.class));
                }
                if (uList.size() > 0) {
                    Collections.sort(uList, (o1, o2) -> String.valueOf(o1.getName()).compareTo(String.valueOf(o2.getName())));
                }
                fbCallback.usersUpdated(uList);
            }
        });

        todo.collection(Document.COLLECTION_LOCATION).addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (queryDocumentSnapshots != null) {
                ArrayList<ToDoLocation> lList = new ArrayList<>();
                for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                    lList.add(qs.toObject(ToDoLocation.class));
                }
                fbCallback.locationsUpdated(lList);
            }
        });
    }
}
