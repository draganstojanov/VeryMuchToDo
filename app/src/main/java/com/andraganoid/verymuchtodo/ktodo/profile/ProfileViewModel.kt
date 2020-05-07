package com.andraganoid.verymuchtodo.ktodo.profile

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.kmodel.Document
import com.andraganoid.verymuchtodo.kmodel.User
import com.andraganoid.verymuchtodo.repository.AuthRepository
import com.andraganoid.verymuchtodo.repository.FirestoreRepository
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import com.andraganoid.verymuchtodo.util.PROFILE_IMAGES_FOLDER
import com.andraganoid.verymuchtodo.util.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.launch

class ProfileViewModel(
        private val preferences: Preferences,
        private val authRepository: AuthRepository,
        private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private var firebaseAuth: FirebaseAuth? = null
    internal lateinit var user: User
    val userName = ObservableField<String>()
    val userMail = ObservableField<String>()
    val profileImage = ObservableField<String>()

    private val _loaderVisibility = MutableLiveData<Boolean>(false)
    val loaderVisibility: LiveData<Boolean>
        get() = _loaderVisibility

    internal val _getImage = MutableLiveData<Int>()
    val getImage: LiveData<Int>
        get() = _getImage


    internal val _editDialog = MutableLiveData<Boolean>()
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
        profileImage.set(user.imageUrl)
    }

    internal fun setMessage(msg: Any) {
        _loaderVisibility.value = false
        dialogMessage = msg
        dialogType = DialogType.MESSAGE
        dialogCancelLabel = R.string.ok
        _editDialog.value = true
    }

    private fun updateUser() {
        preferences.saveUser(user)
        firestoreRepository.updateDocument(Document(user))
    }

    internal fun updateUserName() {
        _editDialog.value = false
        _loaderVisibility.value = true
        viewModelScope.launch {
            val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(userName.get())
                    .build()
            authRepository.updateProfile(profileUpdates)
//        firebaseAuth?.currentUser?.updateProfile(profileUpdates)?
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            setMessage(R.string.sucess)
                            user.name = userName.get()
                            updateUser()
                        } else {
                            setMessage(ERROR_PLACEHOLDER + task.exception.toString())
                        }
                    }
        }
    }


    internal fun updateEmail() {
        _editDialog.value = false
        _loaderVisibility.value = true
        viewModelScope.launch {
            authRepository.updateEmail(userMail.get()!!)
                    // firebaseAuth?.currentUser?.updateEmail(userMail.get()!!)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            setMessage(R.string.sucess)
                            verifyEmail()
                        } else {
                            setMessage(ERROR_PLACEHOLDER + task.exception.toString())
                        }
                    }
        }
    }

    private fun verifyEmail() {
        viewModelScope.launch {
//            firebaseAuth?.currentUser?.sendEmailVerification()
//                    ?.addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            setMessage(R.string.check_mail_for_verification)
//                            user.email = userMail.get()
//                            updateUser()
//                        } else {
//                            setMessage(ERROR_PLACEHOLDER + task.exception.toString())
//                        }
//                    }
            authRepository.verifyEmail()?.addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    setMessage(R.string.check_mail_for_verification)
                    user.email = userMail.get()
                    updateUser()
                } else {
                    setMessage(ERROR_PLACEHOLDER + task.exception.toString())
                }
            }
        }
    }

    internal fun updatePassword() {
        _editDialog.value = false
        _loaderVisibility.value = true
        viewModelScope.launch {
            //  firebaseAuth?.sendPasswordResetEmail(user.email!!)
            authRepository.resetPassword(user.email!!)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            setMessage(R.string.check_mail_for_pass_reset)
                        } else {
                            setMessage(ERROR_PLACEHOLDER + task.exception.toString())
                        }
                    }
        }
    }

    private fun updateImage(uri: Uri) {
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                // .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build()
        authRepository.updateProfile(profileUpdates)
                //  firebaseAuth?.currentUser?.updateProfile(profileUpdates)?
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.imageUrl = uri.toString()
                        profileImage.set(user.imageUrl)
                        updateUser()
                        setMessage(R.string.sucess)
                    } else {
                        setMessage(ERROR_PLACEHOLDER + task.exception.toString())
                    }
                }
    }


    internal fun uploadImage(file: Any?, extension: String) {
        _editDialog.value = false
        _loaderVisibility.value = true
        val storageReference = FirebaseStorage.getInstance().reference
        val name = PROFILE_IMAGES_FOLDER + user.uid + extension
        val imageReference = storageReference.child(name)
        var uploadTask: UploadTask? = null
        when (file) {
            is Uri -> uploadTask = imageReference.putFile(file)
            is ByteArray -> uploadTask = imageReference.putBytes(file)
        }
        uploadTask?.addOnSuccessListener {
            getUri(uploadTask, imageReference)
        }?.addOnFailureListener {
            setMessage(ERROR_PLACEHOLDER + it.toString())
        }
    }

    private fun getUri(uploadTask: UploadTask, imageReference: StorageReference) {
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                setMessage(ERROR_PLACEHOLDER + task.exception.toString())
            }
            imageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateImage(task.result!!)
            } else {
                setMessage(ERROR_PLACEHOLDER + task.exception.toString())
            }
        }
    }
}
