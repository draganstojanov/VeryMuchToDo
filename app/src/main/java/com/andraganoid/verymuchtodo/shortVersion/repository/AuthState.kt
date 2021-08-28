package com.andraganoid.verymuchtodo.shortVersion.repository

sealed class AuthState {
    data class Error(val errorMsg: String) : AuthState()
    object Success : AuthState()
}