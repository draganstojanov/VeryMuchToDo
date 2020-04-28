package com.andraganoid.verymuchtodo.main.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andraganoid.verymuchtodo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterViewModel() : ViewModel() {

    var firebaseAuth: FirebaseAuth? = null

    private val _loaderState = MutableLiveData<Boolean>(false)
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    private val _message = MutableLiveData<Any?>()
    val message: LiveData<Any?>
        get() = _message

    private val _back = MutableLiveData<Boolean>()
    val back: LiveData<Boolean>
        get() = _back

    init {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    fun showMessage(message: Any?) {
        _message.value = message
    }

    fun back() {
        _back.value = true
    }


    fun register(mail: String, pass: String, name: String) {
        _loaderState.value = true
        firebaseAuth?.createUserWithEmailAndPassword(mail, pass)!!
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        updateUser(name)
                        verifyEmail()
                    } else {
                        _message.value = "ERROR: " + task.exception.toString()
                    }
                }
    }

    private fun updateUser(name: String) {
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        // .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
        firebaseAuth?.currentUser?.updateProfile(profileUpdates)
    }

    private fun verifyEmail() {
        firebaseAuth?.currentUser?.sendEmailVerification()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _message.value = R.string.check_mail_for_verification
                        _back.value = true
                    } else {
                        _message.value = "ERROR: " + task.exception.toString()//todo loginerror}
                    }
                }
    }


}