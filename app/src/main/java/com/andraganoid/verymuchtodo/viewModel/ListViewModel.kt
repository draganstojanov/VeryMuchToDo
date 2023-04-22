package com.andraganoid.verymuchtodo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.model.Document
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.old.util.Prefs
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val prefs: Prefs,
    private val authRepository: AuthRepository,
    private val listenersRepository: ListenersRepository,
    private val firestoreRepository: FirestoreRepository,
) : ViewModel() {

    var currentStack: TodoStack? = null
    val userName: String?
        get() = prefs.getUserName()

    fun getSnapshotState(): SharedFlow<StackState?> = listenersRepository.getStackState() //xxx //TODO LISTENEFS


    fun updateItem(item: TodoList?) {
        // val isNew = item?.id.isNullOrEmpty()
        currentStack?.itemList?.removeAll { it.id.equals(item?.id) }
        item?.apply {
//            content = content
//            description = description
            timestamp = System.currentTimeMillis()
            id = "ITEM-$timestamp}"
            this.userName = this@ListViewModel.userName
        }
        //if (isNew) {
        if (item != null) {
            currentStack?.itemList?.add(item)
            updateList()
        }
        //  }


//        _autocompleteItemList.value = _autocompleteItemList.value.also { it?.add(content) }?.toSet()?.toMutableList()
//        prefs.saveAutocompleteItemList(autocompleteItemList.value)
    }

    private fun updateList() {
        viewModelScope.launch { firestoreRepository.updateDocument(Document(currentStack)) }
    }

    override fun onCleared() {
        listenersRepository.remove()
        super.onCleared()
    }

}