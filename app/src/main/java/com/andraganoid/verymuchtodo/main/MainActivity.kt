package com.andraganoid.verymuchtodo.main

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.main.dialog.MessageDialogFragment
import com.andraganoid.verymuchtodo.util.ADMIN_EMAIL
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import com.andraganoid.verymuchtodo.util.MSG_DIALOG_LIST
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.backgorundColor)
    }

    fun loaderVisibility(visibility: Boolean) {
        loader.isVisible = visibility
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    fun showMessage(message: Any?) {
        loaderVisibility(false)
        val msg = arrayListOf<String>()
        when (message) {
            is String -> {
                message.replace(ERROR_PLACEHOLDER, getString(R.string.auth_error))
                msg.add(message)
            }
            is Int -> msg.add(getString(message))
            is ArrayList<*> -> msg.addAll(message as ArrayList<String>)
        }
        val bundle = Bundle()
        bundle.putStringArrayList(MSG_DIALOG_LIST, msg)
        val msgDialogFragment = MessageDialogFragment();
        msgDialogFragment.arguments = bundle
        msgDialogFragment.show(supportFragmentManager, msgDialogFragment.tag)
    }

    fun sendEmailToAdmin() {//todo prebaci u MainActivity
        loaderVisibility(false)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(ADMIN_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, "VeryMuchToDo new User - " + FirebaseAuth.getInstance().currentUser?.displayName)
            putExtra(Intent.EXTRA_TEXT, FirebaseAuth.getInstance().currentUser?.displayName + "\n" + FirebaseAuth.getInstance().currentUser?.email)
            type = "message/rfc822"
        }
        try {
            startActivity(Intent.createChooser(emailIntent,
                    getString(R.string.email_chooser)));
        } catch (ex: ActivityNotFoundException) {
            Log.d("EMAIL", "ERROR")
        }
    }


}



