package com.andraganoid.verymuchtodo.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.model.state.AuthState
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: AuthRepository,
    private val listenersRepository: ListenersRepository,
    private val firestoreRepository: FirestoreRepository,
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState?>(null)
    val authState: StateFlow<AuthState?> get() = _authState

    init {
        getAuthState()
        getDocumentError()
    }

    private fun getAuthState() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.loginCheck()
            authRepository.getAuthState().collect { authState ->
                _authState.value = authState
            }
        }
    }

    private fun getDocumentError() {
        viewModelScope.launch {
            firestoreRepository.getDocumentState().collect { error ->
                _authState.value = AuthState.Error(errorMsg = error)
            }
        }
    }

    fun setFirestoreListeners() {
        listenersRepository.setFirestoreListeners()
    }

}