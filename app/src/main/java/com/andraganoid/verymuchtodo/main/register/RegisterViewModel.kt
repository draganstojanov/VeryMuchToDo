package com.andraganoid.verymuchtodo.main.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var firebaseAuth: FirebaseAuth? = null

    private val _loaderVisibility = MutableLiveData<Boolean>(false)
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

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
        _loaderVisibility.value = true

        viewModelScope.launch {
            authRepository.register(mail, pass)


                    //   firebaseAuth?.createUserWithEmailAndPassword(mail, pass)!!
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            updateUser(name)
                            verifyEmail()
                            sendMailToAdmin()
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

    private fun verifyEmail() {

        viewModelScope.launch { authRepository.verifyEmail().collect { message -> _message.value = message } }

//        firebaseAuth?.currentUser?.sendEmailVerification()
//                ?.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        _message.value = R.string.check_mail_for_verification
//                        _back.value = true
//                    } else {
//                        _message.value = "ERROR: " + task.exception.toString()//todo loginerror}
//                    }
//                }
    }

    private fun sendMailToAdmin() {
      //  authRepository.sendEmailToAdmin()
    }


}