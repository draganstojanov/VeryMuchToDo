package com.andraganoid.verymuchtodo.util

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.old.util.getFormattedDate

@BindingAdapter("messageDialog")
fun messageDialog(tv: TextView, txt: String) {
    tv.apply {
        isVisible = txt.isNotEmpty()
        text = txt
    }
}

@BindingAdapter("userAndDate")
fun userAndDate(tv: TextView, todoStack: TodoStack?) {
    val txt = "${todoStack?.userName}, ${todoStack?.timestamp?.getFormattedDate()}"
    tv.text = txt
}

@BindingAdapter("userAndDate")
fun userAndDate(tv: TextView, todoItem: TodoItem?) {
    val txt = "${todoItem?.userName}, ${todoItem?.timestamp?.getFormattedDate()}"
    tv.text = txt
}

