package com.andraganoid.verymuchtodo.composables

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, message: Any?, isLong: Boolean = false) {//TODO
    Toast.makeText(context, message.toString(), if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}