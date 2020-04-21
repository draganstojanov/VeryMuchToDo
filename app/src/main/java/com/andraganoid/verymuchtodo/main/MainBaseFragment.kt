package com.andraganoid.verymuchtodo.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.main.dialog.MessageDialogFragment
import com.andraganoid.verymuchtodo.util.MSG_DIALOG_LIST


open class MainBaseFragment : Fragment() {

    lateinit var main: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = activity as MainActivity
    }

    fun loaderState(loaderState: Boolean) {
        main.loaderState(loaderState)
    }

    fun hideKeyboard() {
        main.hideKeyboard()
    }

//    fun showMessage(message: Any) {
//        main.showMessage(message)
//    }

    fun showMessage(message: Any?) {
        val msg = arrayListOf<String>()
        when (message) {
            is String -> msg.add(message)
            is Int -> msg.add(getString(message))
            is ArrayList<*> -> msg.addAll(message as ArrayList<String>)
        }

        loaderState(false)
        val bundle = Bundle()
        bundle.putStringArrayList(MSG_DIALOG_LIST, msg)
        val msgDialogFragment = MessageDialogFragment();
        msgDialogFragment.arguments = bundle
        msgDialogFragment.show(main.supportFragmentManager, msgDialogFragment.tag)

    }

}
