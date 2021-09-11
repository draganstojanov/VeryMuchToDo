package com.andraganoid.verymuchtodo.shortVersion.repository


import com.andraganoid.verymuchtodo.shortVersion.model.Document
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepo(private val firebaseFirestore: FirebaseFirestore) {

    //TODO add failed listener

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

    fun deleteMultipleDocument(documents: List<Document>) {
        firebaseFirestore.runBatch { batch ->
            documents.forEach { document ->
                batch.delete(
                    firebaseFirestore
                        .collection(document.collection)
                        .document(document.name)
                )
            }
        }
    }
}