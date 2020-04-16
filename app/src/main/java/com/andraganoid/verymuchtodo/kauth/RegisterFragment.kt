package com.andraganoid.verymuchtodo.kauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.databinding.FragmentRegisterBinding
import com.andraganoid.verymuchtodo.util.isValidConfirmedPassword
import com.andraganoid.verymuchtodo.util.isValidDisplayName
import com.andraganoid.verymuchtodo.util.isValidEmail
import com.andraganoid.verymuchtodo.util.isValidPassword
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class RegisterFragment : BaseAuthFragment() {

    private val viewModel: AuthViewModel by sharedViewModel()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)//tod mozda izbaciti viewbinding
        binding.fragment = this
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }

    fun submitRegistration() {//todo add image
        val mail = registerMailEt.text.toString()
        val name = registerNameEt.text.toString()
        val pass = registerPassEt.text.toString()
        val imgUri = null
        val confirm = registerConfirmPassEt.text.toString()
        val messageArray= arrayListOf<String>()

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
            showMessage(messageArray)
        } else {
            register(mail, pass, name)
        }
    }

    private fun register(mail: String, pass: String, name: String) {
        hideKeyboard()
        showLoader()
        viewModel.auth?.createUserWithEmailAndPassword(mail, pass)!!
                .addOnCompleteListener(main) { task ->
                    if (task.isSuccessful) {
                        updateUser(name)
                        verifyEmail()
                    } else {
                        hideLoader()
                        showMessage("ERROR: " + task.exception.toString())//todo loginerror
                    }
                }
    }


    private fun updateUser(name: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                // .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build()
        viewModel.auth?.currentUser?.updateProfile(profileUpdates)
    }

    private fun verifyEmail() {
        viewModel.auth?.currentUser?.sendEmailVerification()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        hideLoader()
                        showMessage("REGISTRATION AND VERIFICATION SUCCESFULL")
                        backToLogin()
                    } else {
                        showMessage("ERROR: " + task.exception.toString())//todo loginerror}
                    }
                }
    }

    fun backToLogin() {
        main.onBackPressed()
    }
}