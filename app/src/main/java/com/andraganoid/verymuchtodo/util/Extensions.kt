package com.andraganoid.verymuchtodo.util

import android.app.Activity
import android.text.format.DateFormat
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.main.MainActivity
import com.andraganoid.verymuchtodo.main.msgDialog.MessageDialog
import com.andraganoid.verymuchtodo.main.msgDialog.MessageDialogData
import java.util.*

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

fun MainActivity.bottomToast(msg: Any?) {
    MessageDialog(MessageDialogData(toast = msg.toString())).show(supportFragmentManager, MessageDialog::class.simpleName)
}

fun Fragment.bottomToast(msg: Any?) {
    main.bottomToast(msg)
}

fun Fragment.messageDialog(data: MessageDialogData) {
    MessageDialog(data).show(requireActivity().supportFragmentManager, MessageDialog::class.simpleName)
}

fun Fragment.areYouSure(click: () -> Unit) {
    messageDialog(
        MessageDialogData(
            title = "Are you sure?",
            //   desc = resources.getText(R.string.set_widget).toString(),
            btnLeftText = "Cancel",
            btnLeft = {},
            //   btnLeft = this::showHelp,
            btnRightText = "OK",
            btnRight = { click.invoke() })
    )

}

fun Long.getFormattedDate(): String {
    return if (this > 0) {
        val cal = Calendar.getInstance()
        cal.timeInMillis = this
        DateFormat.format("dd.MM.yyyy HH:mm", cal).toString()
    } else {
        ""
    }
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

