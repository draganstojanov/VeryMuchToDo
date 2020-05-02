package com.andraganoid.verymuchtodo.ktodo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment


open class TodoBaseFragment : Fragment() {

    lateinit var todo: TodoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todo = activity as TodoActivity
        Log.d("DDESSTT-BASE", todo.todoNavController.currentDestination?.label.toString())
        var tt = ""
        if (todo.todoNavController.currentDestination?.label!!.equals("ProfileFragment")) {
            tt = "PROFILE"
        } else {
            tt = "OTHER"
        }
        Toast.makeText(todo, tt, Toast.LENGTH_SHORT).show()

    }

    fun loaderState(loaderState: Boolean) {
        todo.loaderState(loaderState)
    }

    fun hideKeyboard() {
        todo.hideKeyboard()
    }

    fun bottomNavBarState(state: Boolean) {
        todo.bottomNavBarState(state)
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
