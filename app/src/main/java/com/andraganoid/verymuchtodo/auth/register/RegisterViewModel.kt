package com.andraganoid.verymuchtodo.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.util.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterViewModel (private val preferences: Preferences) : ViewModel() {

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
        firebaseAuth!!.signOut()
    }

    fun showMessage(message: Any?) {
        _message.value = message
    }

    fun back() {
        _back.value = true
    }


    fun register(mail: String, pass: String, name: String) {
        _loaderState.value = true


        Log.d("WEWEWE", "register")

        firebaseAuth?.createUserWithEmailAndPassword(mail, pass)!!
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d("WEWEWE", "register-ok")
                        //  firebaseUser = firebaseAuth!!.currentUser
                        updateUser(name)
                        verifyEmail()
                    } else {
                        _message.value = "ERROR: " + task.exception.toString()
                    }
                }
    }

    private fun updateUser(name: String) {

        //   Log.d("WEWEWE-USER", firebaseUser.toString())

        Log.d("WEWEWE", "updateUser")
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                // .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build()
        firebaseAuth?.currentUser?.updateProfile(profileUpdates)
    }

    private fun verifyEmail() {
        Log.d("WEWEWE", "verifyEmail")
        // Log.d("WEWEWE-USER-2", firebaseUser.toString())
        firebaseAuth?.currentUser?.sendEmailVerification()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("WEWEWE", "verifyEmail-ok")
                        _message.value = R.string.check_mail_for_verification
                        _back.value = true
                    } else {
                        _message.value = "ERROR: " + task.exception.toString()//todo loginerror}
                    }
                }
    }


}