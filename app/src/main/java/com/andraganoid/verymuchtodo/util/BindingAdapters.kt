package com.andraganoid.verymuchtodo.util


import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.kmodel.Message


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
    fun setRoundImage(iv: ImageView, urlString: String) {
        iv.load(urlString) {
            placeholder(R.drawable.ic_profile_img_placeholder)
            error(R.drawable.ic_profile_img_error_placeholder)
            transformations(CircleCropTransformation())
        }
    }

    @BindingAdapter("senderAndDate")
    @JvmStatic
    fun senderAndDate(tv: TextView, message: Message) {
        val txt = "${if (message.isMyMsg) "" else message.from.name} ${message.timestamp.getFormattedDate()}"
        tv.gravity = if (message.isMyMsg) Gravity.END else Gravity.START
        tv.text = txt
    }


    @BindingAdapter("messageText")
    @JvmStatic
    fun messageText(tv: TextView, message: Message) {
        tv.text = message.text
        val params = tv.layoutParams as ConstraintLayout.LayoutParams
        if (!message.isMyMsg) {
            tv.background = ContextCompat.getDrawable(tv.context, R.drawable.input_background)
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        } else {
            tv.background = ContextCompat.getDrawable(tv.context, R.drawable.button_background)
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }
        tv.layoutParams = params
    }

}

