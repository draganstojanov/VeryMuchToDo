package com.andraganoid.verymuchtodo.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.model.Document
import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.old.util.Prefs
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import kotlinx.coroutines.launch

class StackViewModel(
    private val prefs: Prefs,
    private val authRepository: AuthRepository,
    private val listenersRepository: ListenersRepository,
    private val firestoreRepository: FirestoreRepository,
) : ViewModel() {

    var selectedListId: String? = null

    val userName: String?
        get() = prefs.getUserName()

    fun saveUserName(name: String) {
        prefs.saveUserName(name)
    }

    fun getSnapshotState(): MutableState<StackState?> = mutableStateOf(
        when (val stackStateValue = listenersRepository.stackState.value) {
            is StackState.Stack -> stackStateValue.copy()
            is StackState.Error -> stackStateValue.copy()
            else -> null
        }
    )

    fun changeList(stack: TodoStack?) {
        stack?.apply {
            userName = this@StackViewModel.userName
            timestamp = System.currentTimeMillis()
        }
        if (stack?.id.isNullOrEmpty()) {
            stack?.id = "LIST-${stack?.timestamp}"
            viewModelScope.launch { firestoreRepository.addDocument(Document(stack)) }
        } else {
            viewModelScope.launch { firestoreRepository.updateDocument(Document(stack)) }
        }
    }

    override fun onCleared() {
        listenersRepository.remove()
        super.onCleared()
    }

}