package com.andraganoid.verymuchtodo.repository


import com.andraganoid.verymuchtodo.model.Document
import com.andraganoid.verymuchtodo.util.ResConst
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow

class FirestoreRepository(private val firebaseFirestore: FirebaseFirestore, private val resConst: ResConst) {

    val documentState: MutableSharedFlow<String> = MutableSharedFlow<String>(1)

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
        documentState.tryEmit("${resConst.DOCUMENT_ERROR}: ${exc.localizedMessage}")
    }
}