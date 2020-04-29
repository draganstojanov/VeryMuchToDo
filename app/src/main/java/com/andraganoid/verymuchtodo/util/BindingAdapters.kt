package com.andraganoid.verymuchtodo.util


import android.widget.TextView
import androidx.databinding.BindingAdapter


object BindingAdapters {

    @BindingAdapter("showText")
    @JvmStatic
    fun setTextFromAny(tv: TextView, message: Any?) {
        var txt = ""
        when (message) {
            is String -> txt = message
            is Int -> txt = tv.context.getString(message)
        }
        tv.text = txt
    }


}