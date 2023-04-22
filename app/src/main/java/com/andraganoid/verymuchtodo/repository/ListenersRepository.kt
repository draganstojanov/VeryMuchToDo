package com.andraganoid.verymuchtodo.repository

import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.util.COL_LIST
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ListenersRepository(private val firebaseFirestore: FirebaseFirestore) {

    private lateinit var todoListListener: ListenerRegistration
    private val stackState: MutableSharedFlow<StackState?> = MutableSharedFlow(1)
    fun getStackState(): SharedFlow<StackState?> = stackState

    fun setFirestoreListeners() {
        todoListListener = firebaseFirestore.collection(COL_LIST)
            .addSnapshotListener { snapshots, exc ->
                if (snapshots != null) {
                    val todoStack = arrayListOf<TodoStack?>()
                    snapshots.documents.forEach { documentSnapshot ->
                        todoStack.add(documentSnapshot.toObject(TodoStack::class.java))
                    }
                    // stackState.tryEmit(StackState.Stack(todoList))
                    stackState.tryEmit(StackState.Stack(todoStack))
                } else if (exc != null) {
                    // stackState.tryEmit(StackState.Error(exc.localizedMessage))
                    stackState.tryEmit(StackState.Error(exc.localizedMessage))
                }
            }
    }

    fun remove() {
        todoListListener.remove()
    }

}