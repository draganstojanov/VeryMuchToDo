package com.andraganoid.verymuchtodo.main.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.util.Preferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class LoginViewModel(private val preferences: Preferences) : ViewModel() {
    var firebaseAuth: FirebaseAuth? = null
    var user: User? = null//todo check da li nam ytreba ispis emaila

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean>
        get() = _loginState

    private val _message = MutableLiveData<Any?>()
    val message: LiveData<Any?>
        get() = _message

    init {
        firebaseAuth = FirebaseAuth.getInstance()
     //  firebaseAuth!!.signOut()
        user = preferences.getUser()
        if (firebaseAuth!!.currentUser == null) {
            _loginState.value = false
        } else {
            _loginState.value = firebaseAuth!!.currentUser!!.isEmailVerified
        }
        _loaderVisibility.value = _loginState.value
    }

    fun showMessage(message: Any?) {
        _message.value = message
    }

    fun login(mail: String, pass: String) {
        _loaderVisibility.value = true
        viewModelScope.launch {
            firebaseAuth?.signInWithEmailAndPassword(mail, pass)!!
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            saveUser()
                        } else {
                            _message.value = "ERROR: " + task.exception.toString()
                        }
                    }
        }
    }

    fun resetPassword(mail: String) {
        _loaderVisibility.value = true
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
            preferences.saveUser(User(
                    uid = uid,
                    name = displayName,
                    email = email,
                    photoUrlString = photoUrl.toString()))
        }
        _loginState.value = true


        //todo update user
    }


}