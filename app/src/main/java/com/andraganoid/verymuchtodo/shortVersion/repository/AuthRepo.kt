package com.andraganoid.verymuchtodo.shortVersion.repository


import com.andraganoid.verymuchtodo.secret.LOGIN_EMAIL
import com.andraganoid.verymuchtodo.secret.LOGIN_PASS
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepo(private val firebaseAuth: FirebaseAuth) {

    fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null

    fun login(): Task<AuthResult> = firebaseAuth.signInWithEmailAndPassword(LOGIN_EMAIL, LOGIN_PASS)

}
