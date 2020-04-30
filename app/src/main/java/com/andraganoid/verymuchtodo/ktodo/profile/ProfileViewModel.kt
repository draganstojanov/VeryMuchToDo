package com.andraganoid.verymuchtodo.ktodo.profile

import android.net.Uri
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.util.Preferences
import com.andraganoid.verymuchtodo.util.isValidDisplayName
import com.andraganoid.verymuchtodo.util.isValidEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileViewModel(private val preferences: Preferences) : ViewModel() {

    private var firebaseAuth: FirebaseAuth? = null
    private lateinit var user: User
    val userName = ObservableField<String>()
    val userMail = ObservableField<String>()

    private val _loaderState = MutableLiveData<Boolean>(false)
    val loaderState: LiveData<Boolean>
        get() = _loaderState

//    private val _message = MutableLiveData<Any?>()
//    val message: LiveData<Any?>
//        get() = _message

//    private val _back = MutableLiveData<Boolean>()
//    val back: LiveData<Boolean>
//        get() = _back

    private val _editDialog = MutableLiveData<Boolean>()
    val editDialog: LiveData<Boolean>
        get() = _editDialog

    var dialogMessage: Any = ""
    lateinit var dialogType: DialogType
    var dialogYesLabel: Any = ""
    var dialogNoLabel: Any = ""
    var dialogCancelLabel: Any = ""

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        user = preferences.getUser()
        userName.set(user.name)
        userMail.set(user.email)
    }

//    fun showMessage(message: Any?) {
//        _message.value = message
//    }
//
//    fun back() {
//        _back.value = true
//    }

    fun editButtonClicked(view: View) {
        when (view.id) {
            R.id.profileImgEdit -> {
                dialogMessage = R.string.edit_pic_from
                dialogType = DialogType.IMAGE_UPDATE
                dialogYesLabel = R.string.camera
                dialogNoLabel = R.string.galery
                dialogCancelLabel = R.string.cancel
            }
            R.id.profileNameEdit -> {
                dialogMessage = R.string.edit_name
                dialogType = DialogType.NAME_UPDATE
                dialogYesLabel = R.string.yes
                dialogNoLabel = R.string.no
            }
            R.id.profileMailEdit -> {
                dialogMessage = R.string.edit_mail
                dialogType = DialogType.EMAIL_UPDATE
                dialogYesLabel = R.string.yes
                dialogNoLabel = R.string.no
            }
            R.id.profilePassEdit -> {
                dialogMessage = R.string.edit_pass
                dialogType = DialogType.PASS_UPDATE
                dialogYesLabel = R.string.yes
                dialogNoLabel = R.string.no
            }
        }
        _editDialog.value = true
    }

    fun yesBtn() {
        when (dialogType) {
            DialogType.NAME_UPDATE -> {
                if (userName.get()!!.isValidDisplayName() && !userName.get().equals(user.name)) {
                    updateUserName(userName.get()!!)
                } else {
                    setMessage(R.string.name_unchanged)
                }
            }
            DialogType.EMAIL_UPDATE -> {
                if (userMail.get()!!.isValidEmail() && !userMail.get().equals(user.email)) {
                    updateEmail(userMail.get()!!)
                } else {
                    setMessage(R.string.mail_unchanged)
                }
            }
            DialogType.PASS_UPDATE -> {
                updatePassword(user.email!!)
            }
            DialogType.IMAGE_UPDATE -> {
            }

        }


    }

    fun noBtn() {
        if (dialogType != DialogType.IMAGE_UPDATE) {
            _editDialog.value = false
        } else {
        }
    }

    fun cancelBtn() {
        _editDialog.value = false
    }

    private fun setMessage(msg: Any) {
        _loaderState.value = false
        dialogMessage = msg
        dialogType = DialogType.MESSAGE
        dialogCancelLabel = R.string.ok
        _editDialog.value = true
    }

    private fun updateUser() {
        preferences.saveUser(user)
        //todo update user
    }

    private fun updateUserName(name: String) {
        _editDialog.value = false
        _loaderState.value = true
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
        firebaseAuth?.currentUser?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setMessage(R.string.sucess)
                        user.name = name
                        updateUser()
                    } else {
                        setMessage("ERROR: " + task.exception.toString())
                    }
                }
    }


    private fun updateEmail(email: String) {
        _editDialog.value = false
        _loaderState.value = true
        firebaseAuth?.currentUser?.updateEmail(email)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setMessage(R.string.sucess)
                        verifyEmail()
                    } else {
                        setMessage("ERROR: " + task.exception.toString())
                    }
                }
    }

    private fun verifyEmail() {
        firebaseAuth?.currentUser?.sendEmailVerification()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setMessage(R.string.check_mail_for_verification)
                        user.email = userMail.get()
                        updateUser()
                    } else {
                        setMessage("ERROR: " + task.exception.toString())//todo loginerror}
                    }
                }
    }

    private fun updatePassword(mail: String) {
        _editDialog.value = false
        _loaderState.value = true
        firebaseAuth?.sendPasswordResetEmail(mail)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setMessage(R.string.check_mail_for_pass_reset)
                    } else {
                        setMessage("ERROR: " + task.exception.toString())//todo loginerror}
                    }
                }
    }

    private fun updateImage(name: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
                // .setDisplayName(name)
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build()
        firebaseAuth?.currentUser?.updateProfile(profileUpdates)
    }


}
