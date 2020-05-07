package com.andraganoid.verymuchtodo.ktodo

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment


open class TodoBaseFragment : Fragment() {

    lateinit var todo: TodoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todo = activity as TodoActivity
//        Log.d("DDESSTT-BASE", todo.todoNavController.currentDestination?.label.toString())
//        var tt = ""
//        if (todo.todoNavController.currentDestination?.label!!.equals("ProfileFragment")) {
//            tt = "PROFILE"
//        } else {
//            tt = "OTHER"
//        }
        Toast.makeText(todo, todo.todoNavController.currentDestination?.label.toString(), Toast.LENGTH_SHORT).show()

    }

    fun loaderVisibility(visibility: Boolean) {
        todo.loaderVisibility(visibility)
    }

    fun hideKeyboard() {
        todo.hideKeyboard()
    }

    fun bottomNavBarVisibility(state: Boolean) {
        todo.bottomNavBarVisibility(state)
    }

}
