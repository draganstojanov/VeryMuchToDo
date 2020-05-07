package com.andraganoid.verymuchtodo.repository

import com.andraganoid.verymuchtodo.kmodel.Document
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository(private val firebaseFirestore: FirebaseFirestore) {

    fun addDocument(document: Document) {
        firebaseFirestore
                .collection(document.collection)
                .document(document.name)
                .set(document.values)
    }

    fun deleteDocument(document: Document) {
        firebaseFirestore
                .collection(document.collection)
                .document(document.name)
                .delete()
    }

    fun updateDocument(document: Document) {
        firebaseFirestore
                .collection(document.collection)
                .document(document.name)
                .update(document.values)
    }

}