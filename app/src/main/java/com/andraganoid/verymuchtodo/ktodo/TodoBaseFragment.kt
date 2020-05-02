package com.andraganoid.verymuchtodo.ktodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment


open class TodoBaseFragment : Fragment() {

    lateinit var todo: TodoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todo = activity as TodoActivity

    }

    fun loaderVisibility(visibility: Boolean) {
        todo.loaderVisibility(visibility)
    }

    fun hideKeyboard() {
        todo.hideKeyboard()
    }

    fun bottomNavBarVisibility(state: Boolean) {

        Log.d("dddddd",state.toString())
        todo.bottomNavBarVisibility(state)
    }

}
