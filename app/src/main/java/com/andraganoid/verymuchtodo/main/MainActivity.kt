package com.andraganoid.verymuchtodo.main

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.main.dialog.MessageDialogFragment
import com.andraganoid.verymuchtodo.util.BUNDLE_MSG_DIALOG_LIST
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
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
        bundle.putStringArrayList(BUNDLE_MSG_DIALOG_LIST, msg)
        val msgDialogFragment = MessageDialogFragment();
        msgDialogFragment.arguments = bundle
        msgDialogFragment.show(supportFragmentManager, msgDialogFragment.tag)
    }



}



