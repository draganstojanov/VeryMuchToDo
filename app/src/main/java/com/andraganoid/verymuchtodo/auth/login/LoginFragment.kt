package com.andraganoid.verymuchtodo.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.auth.BaseAuthFragment
import com.andraganoid.verymuchtodo.databinding.FragmentLoginBinding
import com.andraganoid.verymuchtodo.ktodo.TodoActivity
import com.andraganoid.verymuchtodo.util.isValidEmail
import com.andraganoid.verymuchtodo.util.isValidPassword
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginFragment : BaseAuthFragment() {

    // private val viewModel: AuthViewModel by sharedViewModel()
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // viewModel.showMessage(null)
        viewModel.loaderState.observe(viewLifecycleOwner, Observer { loaderState(it) })
        viewModel.loginState.observe(viewLifecycleOwner, Observer { loggedIn ->
            if (loggedIn) {
                Log.d("WEWEWE", loggedIn.toString())

                val todoIntent = Intent(main, TodoActivity::class.java)
                startActivity(todoIntent)
            }
        })
        viewModel.message.observe(viewLifecycleOwner, Observer { message ->
            if (message != null) {
                showMessage(message)
            }
        })

        binding = FragmentLoginBinding.inflate(inflater, container, false)//tod mozda izbaciti viewbinding
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }


    fun submitLogin() {
        val mail = userMailEt.text.toString()
        val pass = userPassEt.text.toString()
        val messageArray = arrayListOf<String>()

        if (!mail.isValidEmail()) {
            messageArray.add(getString(R.string.mail_not_valid))
        }
        if (!pass.isValidPassword()) {
            messageArray.add(getString(R.string.password_not_valid))
        }
        hideKeyboard()
        if (messageArray.size > 0) {
            viewModel.showMessage(messageArray)
        } else {
            viewModel.login(mail, pass)
        }
    }

    fun notRegistered() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    fun forgotPassword() {
        hideKeyboard()
        val mail = userMailEt.text.toString()
        if (mail.isValidEmail()) {
            viewModel.resetPassword(mail)
        } else {
            viewModel.showMessage(R.string.mail_not_valid)
        }
    }


}
