package com.andraganoid.verymuchtodo.base

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.base.dialog.MessageDialogFragment
import com.andraganoid.verymuchtodo.shortVersion.util.ERROR_PLACEHOLDER
import com.andraganoid.verymuchtodo.util.BUNDLE_MSG_DIALOG_LIST
import com.andraganoid.verymuchtodo.util.messageStateFlow
import com.andraganoid.verymuchtodo.util.networkStateFlow
import kotlinx.android.synthetic.main.activity_todo_k.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showMessage(message: Any?) {
        //  loaderVisibility(false)//todo???
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


    fun networkListener() {
        lifecycleScope.launchWhenCreated {
            networkStateFlow.collect { networkState ->
                toast(networkState.toString())
                lostNetworkIcon.isVisible = !networkState
            }
        }
//        lifecycleScope.launchWhenCreated {
//            networkStateChannel.consumeEach {
//                toast(it.toString())
//                lostNetworkIcon.isVisible = !it
//            }
//        }
//
//        networkStatus.observe(this, Observer {
//            Log.d("CCONN", "MAIN-LIVEDATA " + it)
//            toast(it.toString())
//            lostNetworkIcon.isVisible = !it
//        })

    }

    fun errorMessageListener() {
        lifecycleScope.launchWhenCreated {
            messageStateFlow.collect { errorMessage ->
                if (errorMessage.isNotEmpty()) showMessage(errorMessage)
            }
        }
    }

    fun toast(txt: String) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show()
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }


}