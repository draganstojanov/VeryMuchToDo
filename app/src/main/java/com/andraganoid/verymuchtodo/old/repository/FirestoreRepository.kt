package com.andraganoid.verymuchtodo.old.repository


import com.andraganoid.verymuchtodo.old.model.Document
import com.andraganoid.verymuchtodo.util.DOCUMENT_ERROR
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class FirestoreRepository(private val firebaseFirestore: FirebaseFirestore) {

    private val documentState: MutableSharedFlow<String> = MutableSharedFlow(1)
    fun getDocumentState(): SharedFlow<String> = documentState

    fun addDocument(document: Document) {
        firebaseFirestore
            .collection(document.collection)
            .document(document.name)
            .set(document.values)
            .addOnFailureListener { exc -> showErrorMsg(exc) }
    }

    fun deleteDocument(document: Document) {
        firebaseFirestore
            .collection(document.collection)
            .document(document.name)
            .delete()
            .addOnFailureListener { exc -> showErrorMsg(exc) }
    }

    fun updateDocument(document: Document) {
        firebaseFirestore
            .collection(document.collection)
            .document(document.name)
            .update(document.values)
            .addOnFailureListener { exc -> showErrorMsg(exc) }
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
        }.addOnFailureListener { exc -> showErrorMsg(exc) }
    }

    private fun showErrorMsg(exc: Exception) {
        documentState.tryEmit("${DOCUMENT_ERROR}: ${exc.localizedMessage}")
    }
}