package com.andraganoid.verymuchtodo.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.model.state.AuthState
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.repository.ListenersRepository
import com.draganstojanov.myworld_compose.util.debug.debugLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: AuthRepository,
    private val listenersRepository: ListenersRepository,
    private val firestoreRepository: FirestoreRepository,
) : ViewModel() {

    //  val bottomMessageState: MutableState<String?> = mutableStateOf(null)
    val authState: MutableState<AuthState?> = mutableStateOf(null)

    init {
        getAuthState()
        //   getDocumentError()
    }

    private fun getAuthState() {
        viewModelScope.launch(Dispatchers.Main) {
            authRepository.loginCheck()
            authRepository.getAuthState().collect {

                debugLog("AUTHSTATE",it)

                authState.value = it
            }
        }
    }

//    private fun getDocumentError() {
//        viewModelScope.launch {
//            firestoreRepository.getDocumentState().collect { error ->
//                bottomMessageState.value = error
//            }
//        }
//    }

    fun setFirestoreListeners() {
        listenersRepository.setFirestoreListeners()
    }

}