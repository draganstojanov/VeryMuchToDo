package com.andraganoid.verymuchtodo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.model.Document
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.repository.AuthRepo
import com.andraganoid.verymuchtodo.repository.FirestoreRepo
import com.andraganoid.verymuchtodo.repository.ListenersRepo
import com.andraganoid.verymuchtodo.state.StackState
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import com.andraganoid.verymuchtodo.util.Prefs
import com.andraganoid.verymuchtodo.util.logA
import com.andraganoid.verymuchtodo.util.logD
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepo: AuthRepo,
    private val firestoreRepo: FirestoreRepo,
    private val listenersRepo: ListenersRepo,
    private val prefs: Prefs
) : ViewModel() {

    var stack: ArrayList<TodoList?> = arrayListOf()
    var selectedListId: String = ""
    var listForEdit: TodoList = TodoList()
    var itemForEdit: TodoItem = TodoItem()

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    private val _message = MutableLiveData<Any?>()
    val message: LiveData<Any?>
        get() = _message

    private val _userName = MutableLiveData<String>("")
    val userName: LiveData<String>
        get() = _userName

    init {
        _userName.value = prefs.getUserName()

        _loaderVisibility.value = true
        viewModelScope.launch {
            if (authRepo.isLoggedIn()) {
                listenersRepo.setFirestoreListeners()
            } else {
                authRepo.login().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
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

    fun getSnapshotState(): SharedFlow<StackState> = listenersRepo.getSnapshotState()

    fun closeLoader() {
        _loaderVisibility.value = false
    }

    private fun showMessage(msg: Any?) {
        _loaderVisibility.value = false
        _message.value = msg
    }

    override fun onCleared() {
        listenersRepo.remove()
        super.onCleared()
    }

    fun clearItemList() {
        listForEdit.itemList.removeAll { it.completed }
        updateList()
    }

    fun deleteItem(ti: TodoItem) {
        listForEdit.itemList.remove(ti)
        updateList()
    }


    fun addList() {
        listForEdit.apply {
            userName = this@MainViewModel.userName.value
            timestamp = System.currentTimeMillis()
        }
        viewModelScope.launch { firestoreRepo.addDocument(Document(listForEdit)) }
    }

    fun updateList() {
        listForEdit.apply {
            userName = this@MainViewModel.userName.value
            timestamp = System.currentTimeMillis()
        }

//        todoList.completed = true
//        todoList.itemList.forEach { todoItem ->
//            if (!todoItem.completed) {
//                todoList.completed = false
//            }
//        }

        viewModelScope.launch { firestoreRepo.updateDocument(Document(listForEdit)) }
    }

    fun deleteList(todoList: TodoList) {
        viewModelScope.launch { firestoreRepo.deleteDocument(Document(todoList)) }
    }

    fun deleteMultipleList() {

//        stack.filter { todoList -> todoList?.completed == true }.also {
//            if (it.isNullOrEmpty()) {
//                val documentList = arrayListOf<Document>()
//                it.forEach { todoList ->
//                    documentList.add(Document(todoList))
//                }
//                viewModelScope.launch { firestoreRepo.deleteMultipleDocument(documentList) }
//            }
//        }


        stack.removeAll { todoList ->
            todoList?.completed == true
        }
        val documentList = arrayListOf<Document>()
        stack.forEach { todoList -> documentList.add(Document(todoList)) }
        viewModelScope.launch { firestoreRepo.deleteMultipleDocument(documentList) }
    }

    fun checkClearVisibilityStack(): Boolean {
        stack.forEach { tl ->
            if (tl!!.completed) {
                return true
            }
        }
        return false
    }

    fun checkClearVisibilityList(): Boolean {
        listForEdit.itemList.forEach { il ->
            if (il.completed) {
                return true
            }
        }
        return false
    }

    fun saveUserName(name: String) {
        logD(3)
        prefs.saveUserName(name)
        _userName.value = name
    }


    fun addListTest() {//TODO TEST
        val ts = System.currentTimeMillis()
        val test = TodoList(
            timestamp = ts,
            id = ts.toString(),
            description = "Description of new Very Much To Do Stacks",
            title = "Test",
            userName = "Dragan",
            itemList = arrayListOf(
                TodoItem(
                    id = "1-$ts",
                    description = "Description of new Very Much To Do ITEM",
                    completed = false,
                    content = "Test item 1",
                    timestamp = ts,
                    userName = "Dragan"
                ),
                TodoItem(
                    id = "2-$ts",
                    description = "Description of new Very Much To Do ITEM",
                    completed = false,
                    content = "Test item 2",
                    timestamp = ts,
                    userName = "Dragan"
                ),
                TodoItem(
                    id = "3-$ts",
                    description = "Description of new Very Much To Do ITEM",
                    completed = false,
                    content = "Test item 3",
                    timestamp = ts,
                    userName = "Dragan"
                ),
                TodoItem(
                    id = "4-$ts",
                    description = "Description of new Very Much To Do ITEM",
                    completed = false,
                    content = "Test item 4",
                    timestamp = ts,
                    userName = "Dragan"
                ),
            )
        )

        viewModelScope.launch { firestoreRepo.addDocument(Document(test)) }
    }


    fun updateListTest(todoList: TodoList) {//TODO TEST
        val ts = System.currentTimeMillis()

        todoList.timestamp = ts
        todoList.userName = "ddeeed"
        todoList.title = "wwewwewewewewewe"

        todoList.itemList[0].timestamp = ts
        todoList.itemList[0].userName = "eee"

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

