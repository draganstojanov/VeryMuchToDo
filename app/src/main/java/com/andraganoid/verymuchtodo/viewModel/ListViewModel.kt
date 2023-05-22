package com.andraganoid.verymuchtodo.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.launch

class ListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val prefs: Prefs,
    private val authRepository: AuthRepository,
    private val listenersRepository: ListenersRepository,
    private val firestoreRepository: FirestoreRepository,
) : ViewModel() {


    var currentStack: TodoStack? = null
    val userName: String
        //  get() = prefs.getUserName()//TODO TEST VRATI
        get() = "TEST-NAME"

    fun getSnapshotState(): MutableState<StackState?> = mutableStateOf(
        when (val stackStateValue = listenersRepository.stackState.value) {
            is StackState.Stack -> stackStateValue.copy()
            is StackState.Error -> stackStateValue.copy()
            else -> null
        }
    )

    fun updateItem(item: TodoList?) {
        if (item != null) {
            viewModelScope.launch {
                currentStack?.itemList?.removeAll { it.id.equals(item.id) }

                item.apply {
                    timestamp = System.currentTimeMillis()
                    id = "ITEM-$timestamp}"
                    this.userName = this@ListViewModel.userName
                }

                currentStack?.apply {
                    itemList.add(item)
                    timestamp = item.timestamp
                    userName = item.userName
                }

                firestoreRepository.updateDocument(Document(currentStack))
            }
        }


//        _autocompleteItemList.value = _autocompleteItemList.value.also { it?.add(content) }?.toSet()?.toMutableList()
//        prefs.saveAutocompleteItemList(autocompleteItemList.value)
    }


    override fun onCleared() {
        listenersRepository.remove()
        super.onCleared()
    }


}