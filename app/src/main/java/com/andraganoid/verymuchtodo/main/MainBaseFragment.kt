package com.andraganoid.verymuchtodo.main

import android.os.Bundle
import androidx.fragment.app.Fragment


open class MainBaseFragment : Fragment() {

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

    fun sendEmailToAdmin() {
        main.sendEmailToAdmin()
    }

}
