package com.andraganoid.verymuchtodo.ktodo

import android.os.Bundle
import androidx.fragment.app.Fragment


open class TodoBaseFragment : Fragment() {

    lateinit var todo: TodoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todo = activity as TodoActivity
    }

    fun loaderState(loaderState: Boolean) {
        todo.loaderState(loaderState)
    }

    fun hideKeyboard() {
        todo.hideKeyboard()
    }

    fun showMessage(message: Any?) {
//        val msg = arrayListOf<String>()
//        when (message) {
//            is String -> msg.add(message)
//            is Int -> msg.add(getString(message))
//            is ArrayList<*> -> msg.addAll(message as ArrayList<String>)
//        }
//
//        loaderState(false)
//        val bundle = Bundle()
//        bundle.putStringArrayList(MSG_DIALOG_LIST, msg)
//        val msgDialogFragment = MessageDialogFragment();
//        msgDialogFragment.arguments = bundle
//        msgDialogFragment.show(todo.supportFragmentManager, msgDialogFragment.tag)

    }


}
