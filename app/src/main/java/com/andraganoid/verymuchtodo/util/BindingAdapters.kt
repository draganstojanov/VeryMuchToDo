package com.andraganoid.verymuchtodo.util


import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.andraganoid.verymuchtodo.R



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

    @BindingAdapter("roundImage")
    @JvmStatic
    fun setRoundImage(iv: ImageView, urlString: String){
        iv.load(urlString) {
            placeholder(R.drawable.ic_profile_img_placeholder)
            error(R.drawable.ic_profile_img_placeholder)
            transformations(CircleCropTransformation())
        }
    }

}