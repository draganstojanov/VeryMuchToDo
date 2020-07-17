package com.andraganoid.verymuchtodo.ktodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
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

    fun setTitle(title: String) {
        todo.setTitle(title)
    }

    fun toast(txt: String) {
       todo.toast(txt)
    }

}
