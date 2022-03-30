package com.andraganoid.verymuchtodo.util

import android.content.res.Resources
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.main.MainActivity
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialogData
import java.util.*

val Fragment.main: MainActivity
    get() = activity as MainActivity

fun Fragment.hideKeyboard() {
    main.hideKeyboard()
}

fun Fragment.showKeyboard() {
    main.showKeyboard()
}

fun Fragment.showMessage(msg: Any?) {
    main.bottomToast(msg)
}

fun Fragment.invisibleToolbar(isInvisible: Boolean) {
    main.invisibleToolbar(isInvisible)
}


fun Fragment.areYouSure(click: () -> Unit) {
    main.messageDialog(
        MessageDialogData(
            title = getString(R.string.are_you_sure),
            btnLeftText = getString(R.string.cancel),
            btnLeft = {},
            btnRightText = getString(R.string.ok),
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

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
