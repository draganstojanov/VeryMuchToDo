package com.andraganoid.verymuchtodo.model.state

sealed class AuthState {

    data class Error(val errorMsg: String?) : AuthState()
    object Success : AuthState()
    object Cancelled : AuthState()
    object Unchecked : AuthState()
}