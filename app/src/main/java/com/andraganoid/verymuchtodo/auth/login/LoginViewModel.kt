package com.andraganoid.verymuchtodo.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.model.kUser
import com.andraganoid.verymuchtodo.util.Preferences
import com.andraganoid.verymuchtodo.util.firebaseAuth
import kotlinx.coroutines.launch


class LoginViewModel(private val preferences: Preferences) : ViewModel() {


  //  var firebaseAuth: FirebaseAuth? = null
    var user: kUser? = null//todo check da li nam ytreba ispis emaila

    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean>
        get() = _loginState

    private val _message = MutableLiveData<Any?>()
    val message: LiveData<Any?>
        get() = _message

    init {
      //  firebaseAuth = FirebaseAuth.getInstance()
      //  firebaseAuth!!.signOut()

        user = preferences.getUser()

        _loginState.value = firebaseAuth?.currentUser != null && firebaseAuth.currentUser!!.isEmailVerified
        Log.d("WEWEWE-VM", loginState.value.toString())
        _loaderState.value = _loginState.value
    }

    fun showMessage(message: Any?) {
        _message.value = message
    }

    fun login(mail: String, pass: String) {
        _loaderState.value = true
        viewModelScope.launch {

            firebaseAuth?.signInWithEmailAndPassword(mail, pass)!!
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            _message.value = firebaseAuth.currentUser?.email
                            saveUser()
                        } else {
                            _message.value = "ERROR: " + task.exception.toString()
                        }
                    }
        }
    }

    fun resetPassword(mail: String) {
        _loaderState.value = true
        firebaseAuth?.sendPasswordResetEmail(mail)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _message.value = R.string.check_mail_for_pass_reset
                    } else {
                        _message.value = "ERROR: " + task.exception.toString()//todo loginerror
                    }
                }

    }


    fun saveUser() {
        firebaseAuth?.currentUser?.apply {
            preferences.saveUser(kUser(
                    uid = uid,
                    name = displayName,
                    email = email,
                    photoUrl = photoUrl))
        }
        _loginState.value = true


        _message.value = "LOGGED IN"


        //todo update user
    }


}