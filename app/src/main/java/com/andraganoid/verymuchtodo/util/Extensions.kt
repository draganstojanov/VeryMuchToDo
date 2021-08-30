package com.andraganoid.verymuchtodo.util

import android.text.format.DateFormat
import android.util.Patterns
import com.andraganoid.verymuchtodo.kmodel.User
import java.util.*


fun String.isValidEmail() = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean = length in 6..14

fun String.isValidConfirmedPassword(pass: String): Boolean = length in 6..14 && this == pass

fun String.isValidDisplayName(): Boolean = length in 3..23

var myUser = User()

//fun Long.getFormattedDate(): String {
//    return if (this > 0) {
//        val cal = Calendar.getInstance()
//        cal.timeInMillis = this
//        DateFormat.format("dd.MM.yyyy HH:mm", cal).toString()
//    } else {
//        ""
//    }
//}





