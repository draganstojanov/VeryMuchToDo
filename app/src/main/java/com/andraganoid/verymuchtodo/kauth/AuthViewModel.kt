package com.andraganoid.verymuchtodo.kauth

import androidx.lifecycle.ViewModel
import com.andraganoid.verymuchtodo.model.kUser
import com.andraganoid.verymuchtodo.util.Preferences
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel(private val preferences: Preferences) : ViewModel() {

    var auth: FirebaseAuth? = null
//    val userMail = ObservableField<String>("")
//    val userPassword=ObservableField<String>("")
//    val userName=ObservableField<String>("")


    init {
        auth = FirebaseAuth.getInstance()
    }

    fun saveUser() {
        val user = kUser(
                uid = auth?.currentUser!!.uid,
                name = auth?.currentUser!!.displayName!!,
                email = auth?.currentUser!!.email!!,
                photoUrl = auth?.currentUser!!.photoUrl)
        preferences.saveUser(user)


        //todo update user
    }


}
