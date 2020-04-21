package com.andraganoid.verymuchtodo.util

import android.util.Patterns


//val firebaseAuth:FirebaseAuth?=FirebaseAuth.getInstance()

fun String.isValidEmail() = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun String.isValidPassword(): Boolean = length >= 6 && length < 15
fun String.isValidConfirmedPassword(pass: String): Boolean = length >= 6 && length < 15 && this.equals(pass)
fun String.isValidDisplayName(): Boolean = length >= 3 && length < 24
