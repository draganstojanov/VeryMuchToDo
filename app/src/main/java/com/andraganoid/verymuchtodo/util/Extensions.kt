package com.andraganoid.verymuchtodo.util

import android.util.Patterns
import com.andraganoid.verymuchtodo.kmodel.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel


fun String.isValidEmail() = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean = length >= 6 && length < 15

fun String.isValidConfirmedPassword(pass: String): Boolean = length >= 6 && length < 15 && this.equals(pass)

fun String.isValidDisplayName(): Boolean = length >= 3 && length < 24

@ExperimentalCoroutinesApi
val networkStateChannel = ConflatedBroadcastChannel<Boolean>()

var myUser = User()


