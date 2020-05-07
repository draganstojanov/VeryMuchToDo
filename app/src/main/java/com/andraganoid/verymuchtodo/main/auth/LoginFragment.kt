package com.andraganoid.verymuchtodo.main.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.FragmentLoginBinding
import com.andraganoid.verymuchtodo.ktodo.TodoActivity
import com.andraganoid.verymuchtodo.util.isValidEmail
import com.andraganoid.verymuchtodo.util.isValidPassword
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class LoginFragment : AuthBaseFragment() {

    //    private val viewModel: LoginViewModel by viewModel()
    private val viewModel: AuthViewModel by sharedViewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setObservers()
        binding = FragmentLoginBinding.inflate(inflater, container, false)//tod mozda izbaciti viewbinding
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    private fun setObservers() {
        viewModel.loaderVisibility.observe(viewLifecycleOwner, Observer { loaderVisibility(it) })
        viewModel.loginState.observe(viewLifecycleOwner, Observer { loggedIn ->
            if (loggedIn) {
                val todoIntent = Intent(main, TodoActivity::class.java)
                todoIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(todoIntent)
            }
        })
        viewModel.message.observe(viewLifecycleOwner, Observer { message ->
            if (message != null) {
                showMessage(message)
                viewModel.showMessage(null)
            }
        })
       // viewModel.sendEmail.observe(viewLifecycleOwner, Observer { if (it!!) sendEmailToAdmin() })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPassEt.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submitLogin()
                return@OnEditorActionListener true
            }
            false
        })
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
