package com.andraganoid.verymuchtodo.shortVersion.repository

import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.andraganoid.verymuchtodo.shortVersion.util.COL_LIST
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListenersRepo(private val firebaseFirestore: FirebaseFirestore) {

    private lateinit var todoListListener: ListenerRegistration
    private val snapshotState: MutableStateFlow<SnapshotState> = MutableStateFlow(SnapshotState.Unchecked)

    fun getSnapshotState(): StateFlow<SnapshotState> = snapshotState

    fun setFirestoreListeners() {
        todoListListener = firebaseFirestore.collection(COL_LIST)
            .addSnapshotListener { snapshots, exc ->
                if (snapshots != null) {
                    val todoList = arrayListOf<TodoList?>()
                    snapshots.documents.forEach { documentSnapshot ->
                        todoList.add(documentSnapshot.toObject(TodoList::class.java))
                    }
                    snapshotState.value = SnapshotState.TodoLists(todoList)
                }

                if (exc != null) {
                    snapshotState.value = SnapshotState.Error(exc.message)
                }
            }
    }

    fun remove() {
        todoListListener.remove()
    }

}