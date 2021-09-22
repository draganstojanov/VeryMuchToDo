package com.andraganoid.verymuchtodo.util

import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.main.MainActivity
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialogData
import java.util.*

val Fragment.main: MainActivity
    get() = activity as MainActivity

fun Fragment.areYouSure(click: () -> Unit) {
    messageDialog(
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
