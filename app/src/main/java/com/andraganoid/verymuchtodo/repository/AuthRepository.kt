package com.andraganoid.verymuchtodo.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(val firebaseAuth: FirebaseAuth) {

    fun login(mail: String, pass: String): Task<AuthResult> = (firebaseAuth.signInWithEmailAndPassword(mail, pass))

    fun register(mail: String, pass: String): Task<AuthResult> = (firebaseAuth.createUserWithEmailAndPassword(mail, pass))

    fun verifyEmail(): Task<Void>? = firebaseAuth.currentUser?.sendEmailVerification()

    fun resetPassword(mail: String): Task<Void> = firebaseAuth.sendPasswordResetEmail(mail)

}