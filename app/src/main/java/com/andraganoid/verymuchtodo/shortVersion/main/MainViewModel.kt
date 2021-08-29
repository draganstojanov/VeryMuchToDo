package com.andraganoid.verymuchtodo.shortVersion.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList
import com.andraganoid.verymuchtodo.shortVersion.repository.AuthRepo
import com.andraganoid.verymuchtodo.shortVersion.repository.FirestoreRepo
import com.andraganoid.verymuchtodo.shortVersion.repository.ListenersRepo
import com.andraganoid.verymuchtodo.shortVersion.repository.SnapshotState
import com.andraganoid.verymuchtodo.shortVersion.util.ERROR_PLACEHOLDER
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepo: AuthRepo,
    private val firestoreRepo: FirestoreRepo,
    private val listenersRepo: ListenersRepo
) : ViewModel() {


    fun getSnapshotState():StateFlow<SnapshotState> =listenersRepo.getSnapshotState()

//    private val _todoLists = MutableLiveData<ArrayList<TodoList?>>()
//    val todoLists: LiveData<ArrayList<TodoList?>>
//        get() = _todoLists

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


}

