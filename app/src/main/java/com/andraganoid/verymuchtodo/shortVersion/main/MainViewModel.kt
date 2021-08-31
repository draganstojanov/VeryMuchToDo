package com.andraganoid.verymuchtodo.shortVersion.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.shortVersion.model.Document
import com.andraganoid.verymuchtodo.shortVersion.model.TodoItem
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.andraganoid.verymuchtodo.shortVersion.repository.AuthRepo
import com.andraganoid.verymuchtodo.shortVersion.repository.FirestoreRepo
import com.andraganoid.verymuchtodo.shortVersion.repository.ListenersRepo
import com.andraganoid.verymuchtodo.shortVersion.state.StackState
import com.andraganoid.verymuchtodo.shortVersion.util.ERROR_PLACEHOLDER
import com.andraganoid.verymuchtodo.shortVersion.util.logA
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepo: AuthRepo,
    private val firestoreRepo: FirestoreRepo,
    private val listenersRepo: ListenersRepo
) : ViewModel() {



    var selectedListId: String = ""

//    private val _selectedListId = MutableLiveData<String>()
//    val selectedListId: LiveData<String>
//        get() = _selectedListId


    fun getSnapshotState(): SharedFlow<StackState> = listenersRepo.getSnapshotState()

//    fun getStack(): LiveData<StackState> = listenersRepo.getStackState()

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    private val _message = MutableLiveData<Any?>()
    val message: LiveData<Any?>
        get() = _message

    init {
        _loaderVisibility.value = true
        viewModelScope.launch {
            if (authRepo.isLoggedIn()) {
                listenersRepo.setFirestoreListeners()
                showMessage("LOGGED IN")//TODO
            } else {
                authRepo.login().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showMessage("SUCCESS")//TODO
                        listenersRepo.setFirestoreListeners()
                    } else if (task.isCanceled) {
                        showMessage("CANCELLED")//TODO
                    } else {
                        if (task.exception != null) {
                            showMessage(ERROR_PLACEHOLDER + task.exception!!.message)//TODO
                        }
                    }
                }
            }
        }
    }


    private fun showMessage(msg: Any?) {
        _loaderVisibility.value = false
        _message.value = msg
    }

    override fun onCleared() {
        listenersRepo.remove()
        super.onCleared()
    }


    fun addList(todoList: TodoList) {
        viewModelScope.launch { firestoreRepo.addDocument(Document(todoList)) }
    }

    fun updateList(todoList: TodoList) {
        viewModelScope.launch { firestoreRepo.updateDocument(Document(todoList)) }
    }

    fun deleteList(todoList: TodoList) {
        viewModelScope.launch { firestoreRepo.deleteDocument(Document(todoList)) }
    }


    fun addListTest() {//TODO TEST
        val ts = System.currentTimeMillis()
        val test = TodoList(
            timestamp = ts,
            id = ts.toString(),
            completed = false,
            description = "Description of new Very Much To Do Stacks",
            title = "Test",
            userName = "Dragan",
            todoList = listOf(
                TodoItem(
                    id = ts.toString(),
                    description = "Description of new Very Much To Do ITEM",
                    completed = false,
                    content = "Test item",
                    timestamp = ts,
                    userName = "Dragan"
                )
            )
        )

        viewModelScope.launch { firestoreRepo.addDocument(Document(test)) }
    }

    fun updateListTest(todoList: TodoList) {//TODO TEST
        val ts = System.currentTimeMillis()

        todoList.timestamp = ts
        todoList.userName = "ddeeed"
        todoList.title = "wwewwewewewewewe"

        todoList.todoList[0].timestamp = ts
        todoList.todoList[0].userName = "eee"

        logA(todoList)

//        val test = TodoList(
//            timestamp = ts,
//            id = ts.toString(),
//            completed = false,
//            description = "Description of new Very Much To Do Stacks",
//            title = "Test",
//            userName = "Dragan",
//            todoList = listOf(
//                TodoItem(
//                    id = ts.toString(),
//                    description = "Description of new Very Much To Do ITEM",
//                    completed = false,
//                    content = "Test item",
//                    timestamp = ts,
//                    userName = "Dragan"
//                )
//            )
//        )

        viewModelScope.launch { firestoreRepo.addDocument(Document(todoList)) }
    }

//    fun setSelectedListId(id: String) {
//        _selectedListId.value = id
//    }


}

