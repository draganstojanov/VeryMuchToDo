package com.andraganoid.verymuchtodo.old.repository

import com.andraganoid.verymuchtodo.old.model.TodoStack
import com.andraganoid.verymuchtodo.old.model.state.StackState
import com.andraganoid.verymuchtodo.util.COL_LIST
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListenersRepository(private val firebaseFirestore: FirebaseFirestore) {

    private lateinit var todoListListener: ListenerRegistration
    private val stackState: MutableStateFlow<StackState?> = MutableStateFlow(null)
    fun getStackState(): StateFlow<StackState?> = stackState

    fun setFirestoreListeners() {
        todoListListener = firebaseFirestore.collection(COL_LIST)
            .addSnapshotListener { snapshots, exc ->
                if (snapshots != null) {
                    val todoStack = arrayListOf<TodoStack?>()
                    snapshots.documents.forEach { documentSnapshot ->
                        todoStack.add(documentSnapshot.toObject(TodoStack::class.java))
                    }
                   // stackState.tryEmit(StackState.Stack(todoList))
                    stackState.value=StackState.Stack(todoStack)
                }
              else  if (exc != null) {
                   // stackState.tryEmit(StackState.Error(exc.localizedMessage))
                    stackState.value=StackState.Error(exc.localizedMessage)
                }
            }
    }

    fun remove() {
        todoListListener.remove()
    }

}