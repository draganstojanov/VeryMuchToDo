package com.andraganoid.verymuchtodo.shortVersion.repository


import com.andraganoid.verymuchtodo.util.LOGIN_EMAIL
import com.andraganoid.verymuchtodo.util.LOGIN_PASS
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepo(
    //   private val prefs: Prefs,
    private val firebaseAuth: FirebaseAuth
) {

    fun login(mail: String, pass: String): Task<AuthResult> = firebaseAuth.signInWithEmailAndPassword(mail, pass)

    fun register(mail: String, pass: String): Task<AuthResult> = firebaseAuth.createUserWithEmailAndPassword(mail, pass)


   fun authenticate(): AuthState = if (firebaseAuth.currentUser != null) AuthState.Success else login()


    private fun login():AuthState{

      val task:  Task<AuthResult> = firebaseAuth.signInWithEmailAndPassword(LOGIN_EMAIL, LOGIN_PASS)
return if (task.isSuccessful) AuthState.Success else AuthState.Error

    }


}
