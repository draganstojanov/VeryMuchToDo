package com.andraganoid.verymuchtodo.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.shortVersion.main.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
open class AuthBaseFragment : Fragment() {

    lateinit var main: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = activity as MainActivity
    }

    fun loaderVisibility(visibility: Boolean) {
        main.loaderVisibility(visibility)
    }

    fun hideKeyboard() {
        main.hideKeyboard()
    }

    fun showMessage(message: Any?) {
        main.showMessage(message)
    }

//    fun sendEmailToAdmin() {
//        main.sendEmailToAdmin()
//    }

}
