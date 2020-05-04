package com.andraganoid.verymuchtodo.main.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import com.andraganoid.verymuchtodo.util.Preferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class LoginViewModel(private val preferences: Preferences, private val authRepository: AuthRepository) : ViewModel() {
    var firebaseAuth: FirebaseAuth? = null
    var user: User? = null//todo check da li nam ytreba ispis emaila

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    private val _loginState = MutableLiveData<Boolean>(false)
    val loginState: LiveData<Boolean>
        get() = _loginState

    private val _message = MutableLiveData<Any?>()
    val message: LiveData<Any?>
        get() = _message

    private val _sendEmail = MutableLiveData<Boolean?>()
    val sendEmail: LiveData<Boolean?>
        get() = _sendEmail

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        //  firebaseAuth!!.signOut()

        user = preferences.getUser()
        if (firebaseAuth!!.currentUser == null) {
            _loginState.value = false
        } else {
            _loginState.value = firebaseAuth?.currentUser?.isEmailVerified
        }
        _loaderVisibility.value = _loginState.value
    }

    fun showMessage(message: Any?) {
        _message.value = message
    }

    fun login(mail: String, pass: String) {
        _loaderVisibility.value = true
        viewModelScope.launch {
            authRepository.login(mail, pass).addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    if (firebaseAuth?.currentUser?.isEmailVerified!!) {
                        saveUser()
                    } else {
                        verifyEmail()
                        sendMailToAdmin() //todo
                    }
                } else {
                    _message.value = ERROR_PLACEHOLDER + task.exception.toString()
                }
            }
        }
    }

    private fun verifyEmail() {

        viewModelScope.launch { authRepository.verifyEmail().collect { message -> _message.value = message } }

//        firebaseAuth?.currentUser?.sendEmailVerification()
//                ?.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        _message.value = R.string.check_mail_for_verification
//
//                    } else {
//                        _message.value = "ERROR: " + task.exception.toString()//todo loginerror}
//                    }
//                }
    }


    fun resetPassword(mail: String) {

        viewModelScope.launch { authRepository.resetPassword(mail).collect { message -> _message.value = message } }

//        _loaderVisibility.value = true
//        firebaseAuth?.sendPasswordResetEmail(mail)
//                ?.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        _message.value = R.string.check_mail_for_pass_reset
//                    } else {
//                        _message.value = "ERROR: " + task.exception.toString()//todo loginerror
//                    }
//                }
    }


    private fun sendMailToAdmin() {
     //   authRepository.sendEmailToAdmin()
        _sendEmail.value=true
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