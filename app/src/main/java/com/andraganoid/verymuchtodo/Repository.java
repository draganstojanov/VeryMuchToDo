package com.andraganoid.verymuchtodo;

import com.andraganoid.verymuchtodo.model.Document;
import com.google.firebase.firestore.FirebaseFirestore;

public class Repository {
    private FirebaseFirestore todo;
    private static final Repository ourInstance = new Repository();

    public static Repository getInstance() {
        return ourInstance;
    }

    private Repository() {
        todo = FirebaseFirestore.getInstance();
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

}
