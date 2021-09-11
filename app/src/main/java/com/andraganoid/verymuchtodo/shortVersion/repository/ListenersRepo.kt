package com.andraganoid.verymuchtodo.shortVersion.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.andraganoid.verymuchtodo.shortVersion.state.StackState
import com.andraganoid.verymuchtodo.shortVersion.util.COL_LIST
import com.andraganoid.verymuchtodo.shortVersion.util.logA
import com.andraganoid.verymuchtodo.shortVersion.util.logX
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListenersRepo(private val firebaseFirestore: FirebaseFirestore) {

    private lateinit var todoListListener: ListenerRegistration
    private val stackState: MutableSharedFlow<StackState> = MutableSharedFlow<StackState>(1)
//private val stackState: MutableLiveData<StackState> = MutableLiveData()

   fun getSnapshotState(): MutableSharedFlow<StackState> = stackState

  //  fun  getStackState(): LiveData<StackState> = stackState

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
                    stackState.tryEmit(StackState.Error(exc.message))
                }
            }
    }

    fun remove() {
        todoListListener.remove()
    }

}