package com.andraganoid.verymuchtodo.kauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.FragmentLoginBinding
import com.andraganoid.verymuchtodo.ktodo.TodoActivity
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class LoginFragment : BaseAuthFragment() {

    private val viewModel: AuthViewModel by sharedViewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewModel.auth?.currentUser == null) {
            hideLoader()
        } else {
            showMessage("LOGGED IN")
            // todo idi na todoActivity
            //   loggedIn()
            viewModel.auth!!.signOut()
            hideLoader()

        }
        binding = FragmentLoginBinding.inflate(inflater, container, false)//tod mozda izbaciti viewbinding
        //  binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }

    fun submitLogin() {
        val mail = userMailEt.text.toString()
        val pass = userPassEt.text.toString()

        if (!isValidEmail(mail)) {
            showMessage(R.string.mail_not_valid)
        } else {
            if (!isValidPassword(pass)) {
                showMessage(R.string.password_not_valid)
            } else {
                login(mail, pass)
            }
        }
    }

    private fun login(mail: String, pass: String) {
        hideKeyboard()
        showLoader()
        viewModel.auth?.signInWithEmailAndPassword(mail, pass)!!
                .addOnCompleteListener(main) { task ->
                    if (task.isSuccessful) {
                        showMessage(viewModel.auth?.currentUser?.email.toString())
                        viewModel.saveUser()
                        hideLoader()
                        loggedIn()

                    } else {
                        hideLoader()
                        Log.d("LOGIN ERROR: ", task.exception.toString())
                        showMessage("ERROR: " + task.exception.toString())//todo loginerror
                    }
                }

    }

    private fun loggedIn() {

        val todoIntent = Intent(main, TodoActivity::class.java)
        startActivity(todoIntent)

    }

    fun notRegistered() {}

    fun forgotPassword() {}


}
