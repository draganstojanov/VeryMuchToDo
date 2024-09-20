package com.andraganoid.verymuchtodo.repository


import com.andraganoid.verymuchtodo.model.Document
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FirestoreRepository(private val firebaseFirestore: FirebaseFirestore) {

    private val documentState: MutableStateFlow<String> = MutableStateFlow("")
    fun getDocumentState(): StateFlow<String> = documentState.asStateFlow()

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
//        documentState.tryEmit("${DOCUMENT_ERROR}: ${exc.localizedMessage}")
        documentState.value="ERROR: ${exc.localizedMessage}"
    }
}