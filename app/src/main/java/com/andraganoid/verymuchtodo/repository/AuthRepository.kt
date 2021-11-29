package com.andraganoid.verymuchtodo.repository


import com.andraganoid.verymuchtodo.secret.LOGIN_EMAIL
import com.andraganoid.verymuchtodo.secret.LOGIN_PASS
import com.andraganoid.verymuchtodo.model.state.AuthState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepository(private val firebaseAuth: FirebaseAuth) {

    val authState: MutableStateFlow<AuthState> = MutableStateFlow<AuthState>(AuthState.Unchecked)

    fun loginCheck() {
        if (firebaseAuth.currentUser != null) {
            authState.tryEmit(AuthState.Success)
        } else {
            login()
        }
    }

    private fun login() {
        firebaseAuth.signInWithEmailAndPassword(LOGIN_EMAIL, LOGIN_PASS).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> authState.tryEmit(AuthState.Success)
                task.isCanceled -> authState.tryEmit(AuthState.Cancelled)
                else -> createUser()
            }
        }
    }

    private fun createUser() {
        firebaseAuth.createUserWithEmailAndPassword(LOGIN_EMAIL, LOGIN_PASS).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> authState.tryEmit(AuthState.Success)
                task.isCanceled -> authState.tryEmit(AuthState.Cancelled)
                else -> authState.tryEmit(AuthState.Error(task.exception?.localizedMessage))
            }
        }
    }

}
