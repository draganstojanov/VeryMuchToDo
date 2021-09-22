package com.andraganoid.verymuchtodo.util

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

fun logA(msg: Any) {
    Log.d("DEBUG-AAAA", msg.toString())
}

fun logB(msg: Any) {
    Log.d("DEBUG-BBBB", msg.toString())
}

fun logC(msg: Any) {
    Log.d("DEBUG-CCCC", msg.toString())
}

fun logD(msg: Any) {
    Log.d("DEBUG-DDDD", msg.toString())
}

fun logX(tag:Any,msg: Any) {
    Log.d("DEBUG-$tag", msg.toString())
}
