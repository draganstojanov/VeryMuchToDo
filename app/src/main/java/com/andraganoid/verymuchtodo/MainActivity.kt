package com.andraganoid.verymuchtodo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.andraganoid.verymuchtodo.auth.dialog.MessageDialogFragment
import com.andraganoid.verymuchtodo.util.MSG_DIALOG_LIST
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.backgorundColor)

    }

    fun loaderState(loaderState: Boolean) {
        loader.isVisible = loaderState
    }

//    fun showLoader() {
//        loader.isVisible = true
//    }
//
//    fun hideLoader() {
//        loader.isVisible = false
//    }

    fun showMessage(message: Any?) {
        val msg = arrayListOf<String>()
        when (message) {
            is String -> msg.add(message)
            is Int -> msg.add(getString(message))
            is ArrayList<*> -> msg.addAll(message as ArrayList<String>)
        }

        loaderState(false)
        Log.d("WEWEWE-MSG", message.toString())
        val bundle = Bundle()
        bundle.putStringArrayList(MSG_DIALOG_LIST, msg)
        val msgDialogFragment = MessageDialogFragment();
        msgDialogFragment.arguments = bundle
        msgDialogFragment.show(supportFragmentManager, msgDialogFragment.tag)

        // Toast.makeText(this, msg, Toast.LENGTH_LONG).show()//todo bottomSheetFragment
    }


    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}



