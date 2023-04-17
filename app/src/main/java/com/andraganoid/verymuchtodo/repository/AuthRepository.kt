package com.andraganoid.verymuchtodo.repository


import com.andraganoid.verymuchtodo.model.state.AuthState
import com.andraganoid.verymuchtodo.util.LOGIN_EMAIL
import com.andraganoid.verymuchtodo.util.LOGIN_PASS
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    private val authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Unchecked)
    fun getAuthState(): StateFlow<AuthState> = authState

    fun loginCheck() {
        if (firebaseAuth.currentUser != null) {
            authState.value = AuthState.Success
        } else {
            login()
        }
    }

    private fun login() {
        firebaseAuth.signInWithEmailAndPassword(LOGIN_EMAIL, LOGIN_PASS).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> authState.value = AuthState.Success
                task.isCanceled -> authState.value = AuthState.Cancelled
                else -> createUser()
            }
        }
    }

    private fun createUser() {
        firebaseAuth.createUserWithEmailAndPassword(LOGIN_EMAIL, LOGIN_PASS).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> authState.value = AuthState.Success
                task.isCanceled -> authState.value = AuthState.Cancelled
                else -> authState.value = AuthState.Error(task.exception?.localizedMessage)
            }
        }
    }

}
