package com.andraganoid.verymuchtodo.shortVersion.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.shortVersion.main.MainActivity

//val Fragment.checkIcon: Drawable?
//    get() = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check)

val Fragment.main: MainActivity
    get() = activity as MainActivity

fun MainActivity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    main.hideKeyboard()
}

//val Int.toDp: Int
//    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
//
//val Int.toPx: Int
//    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
//
//val Int.toPxFloat: Float
//    get() = (this * Resources.getSystem().displayMetrics.density)
//
//val Int.toSp: Float
//    get() = (this / Resources.getSystem().displayMetrics.scaledDensity)
//
//val Float.toDp: Float
//    get() = (this / Resources.getSystem().displayMetrics.density)
//
//val Float.toPx: Float
//    get() = (this * Resources.getSystem().displayMetrics.density)
//
//val Context.screenWidth
//    get() = this.resources.displayMetrics.widthPixels
//
//fun Long.timeFormatter(): String {
//    var secs = (this / 1000).toInt()
//    val mins = secs / 60
//    secs %= 60
//    return "$mins:${if (secs < 10) "0" else ""}$secs"
//}
//
//fun Long.timeFormatterMillis(): String {
//    var secs = (this / 1000).toInt()
//    val mins = secs / 60
//    secs %= 60
//    val mils = ((this % 1000) / 10).toInt()
//    return "$mins.${if (secs < 10) "0" else ""}$secs:${if (mils < 10) "0" else ""}$mils"
//}
//

