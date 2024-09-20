package com.andraganoid.verymuchtodo.model.state

sealed class AuthState {

    data class Error(val errorMsg: String?) : AuthState()
    data object Success : AuthState()
    data object Cancelled : AuthState()
    data object Unchecked : AuthState()
}