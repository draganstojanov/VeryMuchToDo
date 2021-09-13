package com.andraganoid.verymuchtodo.util

import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.main.MainActivity
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialogData
import java.util.*

val Fragment.main: MainActivity
    get() = activity as MainActivity

fun Fragment.areYouSure(click: () -> Unit) {
    messageDialog(
        MessageDialogData(
            title = "Are you sure?",
            btnLeftText = "Cancel",
            btnLeft = {},
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
