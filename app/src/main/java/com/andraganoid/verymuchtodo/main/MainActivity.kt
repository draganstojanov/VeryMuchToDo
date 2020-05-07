package com.andraganoid.verymuchtodo.main

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.main.dialog.MessageDialogFragment
import com.andraganoid.verymuchtodo.util.BUNDLE_MSG_DIALOG_LIST
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import com.andraganoid.verymuchtodo.util.networkStateChannel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach

class MainActivity : AppCompatActivity() {

    lateinit var mainNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNavigationListener()

        networkListener()



        window.statusBarColor = ContextCompat.getColor(this, R.color.backgorundColor)

    }

   @ObsoleteCoroutinesApi
   @ExperimentalCoroutinesApi
   private fun networkListener() {
        lifecycleScope.launchWhenCreated {
            networkStateChannel.consumeEach { toast(it.toString())
            lostNetworkIcon.isVisible=!it
            }
        }
    }

    private fun setNavigationListener() {
        mainNavController = findNavController(R.id.fragmentLayout)
        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            var backArrow = false
            if (destination.label!!.equals(getString(R.string.register_frag_label))) {
                backArrow = true
            }
            backIcon.isVisible = backArrow
        }
    }

    fun loaderVisibility(visibility: Boolean) {
        loader.isVisible = visibility
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    fun backClicked(view: View) {
        mainNavController.popBackStack()
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

    private fun toast(txt: String) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
    }
}



