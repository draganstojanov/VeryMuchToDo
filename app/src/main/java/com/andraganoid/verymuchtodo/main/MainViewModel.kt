package com.andraganoid.verymuchtodo.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.model.Document
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.model.state.AuthState
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import com.andraganoid.verymuchtodo.util.*
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository,
    private val listenersRepository: ListenersRepository,
    private val prefs: Prefs,
    private val resConst: ResConst
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

    private val _autocompleteItemList = MutableLiveData<MutableList<String>>()
    val autocompleteItemList: LiveData<MutableList<String>>
        get() = _autocompleteItemList

    init {
        _userName.value = prefs.getUserName()
        _loaderVisibility.value = true

        viewModelScope.launch {
            authRepository.loginCheck()
            authRepository.authState.collect { authState ->
                when (authState) {
                    is AuthState.Success -> listenersRepository.setFirestoreListeners()
                    is AuthState.Error -> showMessage("${resConst.ERROR_PLACEHOLDER}: ${authState.errorMsg}")
                    is AuthState.Cancelled -> showMessage(resConst.CANCELLED)
                    else -> Timber.d("Not logged in")
                }
            }
        }

        getDocumentError()
        _autocompleteItemList.value = prefs.getAutocompleteIemList() ?: mutableListOf()
    }

    fun getSnapshotState(): SharedFlow<StackState> = listenersRepository.stackState

    private fun getDocumentError() {
        viewModelScope.launch { firestoreRepository.documentState.collect { error -> showMessage(error) } }
    }

    fun closeLoader() {
        _loaderVisibility.value = false
    }

    fun showMessage(msg: Any?) {
        _loaderVisibility.value = false
        _message.value = msg
    }

    override fun onCleared() {
        listenersRepository.remove()
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

    fun updateItem(content: String, description: String, isNewItem: Boolean) {
        itemForEdit.apply {
            this.content = content
            this.description = description
            timestamp = System.currentTimeMillis()
            id = "ITEM-$timestamp}"
            this.userName = this@MainViewModel.userName.value
        }
        if (isNewItem) {
            listForEdit.itemList.add(itemForEdit)
        }
        updateList()

        _autocompleteItemList.value = _autocompleteItemList.value.also { it?.add(content) }
        prefs.saveAutocompleteItemList(autocompleteItemList.value)
    }

    fun changeList(title: String, description: String, isNewList: Boolean) {
        listForEdit.apply {
            this.title = title
            this.description = description
            userName = this@MainViewModel.userName.value
            timestamp = System.currentTimeMillis()
        }
        if (isNewList) {
            listForEdit.id = "LIST-${listForEdit.timestamp}"
            viewModelScope.launch { firestoreRepository.addDocument(Document(listForEdit)) }
        } else {
            viewModelScope.launch { firestoreRepository.updateDocument(Document(listForEdit)) }
        }
    }

    fun updateList() {
        listForEdit.apply {
            userName = this@MainViewModel.userName.value
            timestamp = System.currentTimeMillis()
        }
        viewModelScope.launch { firestoreRepository.updateDocument(Document(listForEdit)) }
    }

    fun deleteList(todoList: TodoList) {
        viewModelScope.launch { firestoreRepository.deleteDocument(Document(todoList)) }
    }

    fun deleteMultipleList() {
        stack.removeAll { todoList ->
            todoList?.completed == false
        }
        val documentList = arrayListOf<Document>()
        stack.forEach { todoList -> documentList.add(Document(todoList)) }
        viewModelScope.launch { firestoreRepository.deleteMultipleDocument(documentList) }
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
        listForEdit.itemList.forEach { ti ->
            if (ti.completed) {
                return true
            }
        }
        return false
    }

    fun saveUserName(name: String) {
        prefs.saveUserName(name)
        _userName.value = name
    }

}

