package com.andraganoid.verymuchtodo.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.FragmentRegisterBinding
import com.andraganoid.verymuchtodo.util.isValidConfirmedPassword
import com.andraganoid.verymuchtodo.util.isValidDisplayName
import com.andraganoid.verymuchtodo.util.isValidEmail
import com.andraganoid.verymuchtodo.util.isValidPassword
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class RegisterFragment : AuthBaseFragment() {

    private val viewModel: AuthViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setObservers()
        binding = FragmentRegisterBinding.inflate(inflater, container, false)//todo mozda izbaciti viewbinding
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    private fun setObservers() {
        viewModel.back.observe(viewLifecycleOwner, Observer { back ->
            back.let {
                if (it) {
                    main.mainNavController.popBackStack()
                    viewModel._back.value=false
                }
            }
        })
        viewModel.loaderVisibility.observe(viewLifecycleOwner, Observer { loaderVisibility(it) })
        viewModel.message.observe(viewLifecycleOwner, Observer { message ->
            if (message != null) {
                showMessage(message)
                viewModel.showMessage(null)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerConfirmPassEt.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                submitRegistration()
                return@OnEditorActionListener true
            }
            false
        })
    }

    fun submitRegistration() {

        hideKeyboard()
        val mail = registerMailEt.text.toString()
        val name = registerNameEt.text.toString()
        val pass = registerPassEt.text.toString()
        val confirm = registerConfirmPassEt.text.toString()
        val messageArray = arrayListOf<String>()

        if (!mail.isValidEmail()) {
            messageArray.add(getString(R.string.mail_not_valid))
        }
        if (!name.isValidDisplayName()) {
            messageArray.add(getString(R.string.name_not_valid))
        }
        if (!pass.isValidPassword()) {
            messageArray.add(getString(R.string.password_not_valid))
        }
        if (!confirm.isValidConfirmedPassword(pass)) {
            messageArray.add(getString(R.string.confirm_not_valid))
        }

        if (messageArray.size > 0) {
            viewModel.showMessage(messageArray)
        } else {
            viewModel.register(mail, pass, name)
        }
    }


}