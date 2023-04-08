package com.andraganoid.verymuchtodo.old.repository

import com.andraganoid.verymuchtodo.old.model.TodoList
import com.andraganoid.verymuchtodo.old.model.state.StackState
import com.andraganoid.verymuchtodo.util.COL_LIST
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ListenersRepository(private val firebaseFirestore: FirebaseFirestore) {

    private lateinit var todoListListener: ListenerRegistration
    private val stackState: MutableSharedFlow<StackState> = MutableSharedFlow(1)
    fun getStackState(): SharedFlow<StackState> = stackState

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