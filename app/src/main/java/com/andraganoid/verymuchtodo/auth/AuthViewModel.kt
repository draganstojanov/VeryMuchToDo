package com.andraganoid.verymuchtodo.auth

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.kmodel.Document
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.shortVersion.util.ERROR_PLACEHOLDER

import com.andraganoid.verymuchtodo.util.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.launch

class AuthViewModel(
        private val preferences: Preferences,
        private val authRepository: AuthRepository,
        private val firestoreRepository: FirestoreRepository)
    : ViewModel() {
    var firebaseAuth: FirebaseAuth? = null
    var user: User? = null

    val passIsVisible = ObservableBoolean(false)

    private val _loaderVisibility = MutableLiveData<Boolean>()
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    private val _loginState = MutableLiveData<Boolean>(false)
    val loginState: LiveData<Boolean>
        get() = _loginState

    private val _message = MutableLiveData<Any?>()
    val message: LiveData<Any?>
        get() = _message

//    private val _sendEmail = MutableLiveData<Boolean?>()
//    val sendEmail: LiveData<Boolean?>
//        get() = _sendEmail

    val _back = MutableLiveData<Boolean>()
    val back: LiveData<Boolean>
        get() = _back

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        user = preferences.getMyUser()
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

    fun back() {
        _back.value = true
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
                        //    sendMailToAdmin() //todo
                    }
                } else {
                    _message.value = ERROR_PLACEHOLDER + task.exception.toString()
                }
            }
        }
    }

    private fun verifyEmail() {
        viewModelScope.launch {
            authRepository.verifyEmail()?.addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    _message.value = R.string.check_mail_for_verification
                } else {
                    _message.value = ERROR_PLACEHOLDER + task.exception.toString()
                }
            }
        }
    }


    fun resetPassword(mail: String) {
        viewModelScope.launch {
            authRepository.resetPassword(mail).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _message.value = R.string.check_mail_for_pass_reset
                } else {
                    _message.value = ERROR_PLACEHOLDER + task.exception.toString()
                }
            }
        }
    }


//    private fun sendMailToAdmin() {
//        _sendEmail.value = true
//    }

    fun register(mail: String, pass: String, name: String) {
        _loaderVisibility.value = true

        viewModelScope.launch {
            authRepository.register(mail, pass).addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    updateUser(name)
                    verifyEmail()
                    //  sendMailToAdmin()
                } else {
                    _message.value = ERROR_PLACEHOLDER + task.exception.toString()
                }
            }
        }
    }

    private fun updateUser(name: String) {
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        firebaseAuth?.currentUser?.updateProfile(profileUpdates)
    }


    fun saveUser() {//todo to Repository
        var user = User()
        firebaseAuth?.currentUser?.apply {
            user = User(
                    uid = uid,
                    name = displayName,
                    email = email,
                    imageUrl = photoUrl.toString())
        }
        preferences.saveMyUser(user)
        firestoreRepository.addDocument(Document(user))
        _loginState.value = true


        //todo update user
    }


}