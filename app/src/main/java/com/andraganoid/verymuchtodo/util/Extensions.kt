package com.andraganoid.verymuchtodo.util

import android.text.format.DateFormat
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andraganoid.verymuchtodo.kmodel.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.util.*


fun String.isValidEmail() = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean = length >= 6 && length < 15

fun String.isValidConfirmedPassword(pass: String): Boolean = length >= 6 && length < 15 && this.equals(pass)

fun String.isValidDisplayName(): Boolean = length >= 3 && length < 24

var myUser = User()

//@ExperimentalCoroutinesApi
//val _networkStateFlow = MutableStateFlow<Boolean>(false)
//@ExperimentalCoroutinesApi
//val networkStateFlow: StateFlow<Boolean> = _networkStateFlow

@ExperimentalCoroutinesApi
val networkStateChannel = ConflatedBroadcastChannel<Boolean>()

val _networkStatus = MutableLiveData<Boolean>()
val networkStatus: LiveData<Boolean>
    get() = _networkStatus

fun Long.getFormattedDate(): String {
    return if (this > 0) {
        val cal = Calendar.getInstance()
        cal.setTimeInMillis(this)
        DateFormat.format("dd.MM.yyyy HH:mm", cal).toString()
    } else {
        ""
    }
}





