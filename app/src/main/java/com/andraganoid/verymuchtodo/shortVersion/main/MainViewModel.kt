package com.andraganoid.verymuchtodo.shortVersion.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.shortVersion.repository.AuthRepo
import com.andraganoid.verymuchtodo.shortVersion.repository.AuthState
import com.andraganoid.verymuchtodo.shortVersion.repository.FirestoreRepo
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepo: AuthRepo,
    private val firestoreRepo: FirestoreRepo
) : ViewModel() {

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    private val _message = MutableLiveData<Any?>()
    val message: LiveData<Any?>
        get() = _message

    init {
        _loaderVisibility.value = true
        viewModelScope.launch {

            when (val auth = authRepo.authenticate()) {
                is AuthState.Success -> {
                    _message.value = "SUCCESS"//TODO
                }
                is AuthState.Error -> _message.value = ERROR_PLACEHOLDER + auth.errorMsg
            }


        }

    }


}