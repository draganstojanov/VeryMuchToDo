package com.andraganoid.verymuchtodo.shortVersion.util

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.andraganoid.verymuchtodo.model.TodoList

@BindingAdapter("messageDialog")
fun messageDialog(tv: TextView, txt: String) {
    tv.apply {
        isVisible = txt.isNotEmpty()
        text = txt
    }
}

@BindingAdapter("userAndDate")
fun userAndDate(tv: TextView, todoList: com.andraganoid.verymuchtodo.shortVersion.model.TodoList) {
    val txt = "${{ todoList.userName}}, ${todoList.timestamp?.getFormattedDate()}"
    tv.text = txt
}

