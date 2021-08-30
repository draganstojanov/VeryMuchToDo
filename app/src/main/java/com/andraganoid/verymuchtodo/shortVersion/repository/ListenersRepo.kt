package com.andraganoid.verymuchtodo.shortVersion.repository

import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.andraganoid.verymuchtodo.shortVersion.state.StackState
import com.andraganoid.verymuchtodo.shortVersion.util.COL_LIST
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListenersRepo(private val firebaseFirestore: FirebaseFirestore) {

    private lateinit var todoListListener: ListenerRegistration
    private val stackState: MutableStateFlow<StackState> = MutableStateFlow(StackState.Unchecked)

    fun getSnapshotState(): StateFlow<StackState> = stackState

    fun setFirestoreListeners() {
        todoListListener = firebaseFirestore.collection(COL_LIST)
            .addSnapshotListener { snapshots, exc ->
                if (snapshots != null) {
                    val todoList = arrayListOf<TodoList?>()
                    snapshots.documents.forEach { documentSnapshot ->
                        todoList.add(documentSnapshot.toObject(TodoList::class.java))
                    }
                    stackState.value = StackState.Stack(todoList)
                }

                if (exc != null) {
                    stackState.value = StackState.Error(exc.message)
                }
            }
    }

    fun remove() {
        todoListListener.remove()
    }

}