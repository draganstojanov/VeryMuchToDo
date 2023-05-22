package com.andraganoid.verymuchtodo.repository

import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.util.COL_LIST
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListenersRepository(private val firebaseFirestore: FirebaseFirestore) {

    private lateinit var todoListListener: ListenerRegistration

    private val _stackState: MutableStateFlow<StackState?> = MutableStateFlow(null)
    val stackState: StateFlow<StackState?> = _stackState

    fun setFirestoreListeners() {
        todoListListener = firebaseFirestore.collection(COL_LIST)
            .addSnapshotListener { snapshots, exc ->
                if (snapshots != null) {
                    val todoStack = arrayListOf<TodoStack?>()
                    snapshots.documents.forEach { documentSnapshot ->
                        todoStack.add(documentSnapshot.toObject(TodoStack::class.java))
                    }
                    _stackState.value = StackState.Stack(todoStack)
                } else if (exc != null) {
                    _stackState.value = StackState.Error(exc.localizedMessage)
                }
            }
    }

    fun remove() {
        todoListListener.remove()
    }

}