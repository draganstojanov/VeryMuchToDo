package com.andraganoid.verymuchtodo.util


import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter


object BindingAdapters {

    @BindingAdapter("showText")
@JvmStatic
    fun setTextFromAny(tv: TextView, message: Any?) {
        var txt = ""
        Log.d("sdsdsd",message.toString())
        when (message) {
            is String -> txt = message
            is Int -> txt = tv.context.getString(message)
        }
        Log.d("sdsdsd2",txt.toString())
        tv.text = txt
    }


}