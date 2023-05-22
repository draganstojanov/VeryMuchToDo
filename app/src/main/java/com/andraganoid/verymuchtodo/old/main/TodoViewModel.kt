package com.andraganoid.verymuchtodo.old.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.model.Document
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.isCompleted
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.old.util.Prefs
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(
    private val firestoreRepository: FirestoreRepository,
    private val listenersRepository: ListenersRepository,
    private val prefs: Prefs
) : ViewModel() {

    var stack: ArrayList<TodoStack?> = arrayListOf()
    var selectedListId: String = ""
    var listForEdit: TodoStack = TodoStack()
    var itemForEdit: TodoList = TodoList()

    private val _userName = MutableLiveData("")
    val userName: LiveData<String> get() = _userName

    private val _autocompleteItemList = MutableLiveData<MutableList<String>>()
    val autocompleteItemList: LiveData<MutableList<String>> get() = _autocompleteItemList

    init {
        _userName.value = prefs.getUserName()
        checkAutocompleteList()
    }

    fun checkAutocompleteList() {
        _autocompleteItemList.value = prefs.getAutocompleteIemList() ?: mutableListOf()
    }

    fun getSnapshotState(): StateFlow<StackState?> = listenersRepository.stackState

    override fun onCleared() {
        listenersRepository.remove()
        super.onCleared()
    }

    fun clearItemList() {
        listForEdit.itemList.removeAll { it.completed }
        updateList()
    }

    fun getSortedItemList() = listForEdit.itemList.sortedWith(
        compareBy(
            { it.completed },
            { it.timestamp }
        )
    )

    fun deleteItem(ti: TodoList) {
        listForEdit.itemList.remove(ti)
        updateList()
    }

    fun updateItem(content: String, description: String, isNewItem: Boolean) {
        itemForEdit.apply {
            this.content = content
            this.description = description
            timestamp = System.currentTimeMillis()
            id = "ITEM-$timestamp}"
            this.userName = this@TodoViewModel.userName.value
        }
        if (isNewItem) {
            listForEdit.itemList.add(itemForEdit)
        }
        updateList()

        _autocompleteItemList.value = _autocompleteItemList.value.also { it?.add(content) }?.toSet()?.toMutableList()
        prefs.saveAutocompleteItemList(autocompleteItemList.value)
    }

    fun changeList(title: String, description: String, isNewList: Boolean) {
        listForEdit.apply {
            this.title = title
            this.description = description
            userName = this@TodoViewModel.userName.value
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
            userName = this@TodoViewModel.userName.value
            timestamp = System.currentTimeMillis()
        }
        viewModelScope.launch { firestoreRepository.updateDocument(Document(listForEdit)) }
    }

    fun deleteList(todoStack: TodoStack) {
        viewModelScope.launch { firestoreRepository.deleteDocument(Document(todoStack)) }
    }

    fun deleteMultipleList() {
        stack.removeAll { todoList ->
            todoList?.isCompleted() == false
        }
        val documentList = arrayListOf<Document>()
        stack.forEach { todoList -> documentList.add(Document(todoList)) }
        viewModelScope.launch { firestoreRepository.deleteMultipleDocument(documentList) }
    }

    fun checkClearVisibilityStack(): Boolean {
        stack.forEach { tl ->
            if (tl!!.isCompleted()) {
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

