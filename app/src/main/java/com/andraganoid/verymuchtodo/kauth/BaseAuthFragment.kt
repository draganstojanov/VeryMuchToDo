package com.andraganoid.verymuchtodo.kauth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.MainActivity


open class BaseAuthFragment : Fragment() {


    lateinit var main: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = activity as MainActivity
    }

    fun showLoader() {
        main.showLoader()
    }

    fun hideLoader() {
        main.hideLoader()
    }

    fun hideKeyboard() {
        main.hideKeyboard()
    }

    fun showMessage(message: Any) {
        main.showMessage(message)
    }


    fun isValidEmail(mail: String) = !mail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()
    fun isValidPassword(pass: String): Boolean = pass.length >= 6


}
