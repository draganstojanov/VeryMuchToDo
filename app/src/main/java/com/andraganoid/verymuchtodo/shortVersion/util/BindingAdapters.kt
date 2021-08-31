package com.andraganoid.verymuchtodo.shortVersion.util

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.andraganoid.verymuchtodo.shortVersion.model.TodoItem
import com.andraganoid.verymuchtodo.shortVersion.model.TodoList

@BindingAdapter("messageDialog")
fun messageDialog(tv: TextView, txt: String) {
    tv.apply {
        isVisible = txt.isNotEmpty()
        text = txt
    }
}

@BindingAdapter("userAndDate")
fun userAndDate(tv: TextView, todoList: TodoList?) {
    val txt = "${{ todoList?.userName }}, ${todoList?.timestamp?.getFormattedDate()}"
    tv.text = txt
}

@BindingAdapter("userAndDate")
fun userAndDate(tv: TextView, todoItem: TodoItem?) {
    val txt = "${{ todoItem?.userName }}, ${todoItem?.timestamp?.getFormattedDate()}"
    tv.text = txt
}

