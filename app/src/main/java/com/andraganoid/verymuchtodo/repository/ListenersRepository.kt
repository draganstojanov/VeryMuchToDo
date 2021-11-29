package com.andraganoid.verymuchtodo.repository

import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.util.COL_LIST
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableSharedFlow

class ListenersRepository(private val firebaseFirestore: FirebaseFirestore) {

    private lateinit var todoListListener: ListenerRegistration
    val stackState: MutableSharedFlow<StackState> = MutableSharedFlow<StackState>(1)

    fun setFirestoreListeners() {
        todoListListener = firebaseFirestore.collection(COL_LIST)
            .addSnapshotListener { snapshots, exc ->
                if (snapshots != null) {
                    val todoList = arrayListOf<TodoList?>()
                    snapshots.documents.forEach { documentSnapshot ->
                        todoList.add(documentSnapshot.toObject(TodoList::class.java))
                    }
                    stackState.tryEmit(StackState.Stack(todoList))
                }
                if (exc != null) {
                    stackState.tryEmit(StackState.Error(exc.localizedMessage))
                }
            }
    }

    fun remove() {
        todoListListener.remove()
    }

}