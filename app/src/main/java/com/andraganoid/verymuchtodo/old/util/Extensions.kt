package com.andraganoid.verymuchtodo.old.util

import android.content.res.Resources
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.old.main.MainActivityOld
import com.andraganoid.verymuchtodo.old.ui.msgDialog.MessageDialogData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar


val Fragment.main: MainActivityOld
    get() = activity as MainActivityOld

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
            btnLeft = null,
            btnRightText = getString(R.string.ok),
            btnRight = { click.invoke() })
    )
}

fun Long.getFormattedDate(): String =
    if (this > 0) {
        val cal = Calendar.getInstance()
        cal.timeInMillis = this
        DateFormat.format("dd.MM.yyyy HH:mm", cal).toString()
    } else {
        ""
    }

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Long.getFormattedDateShort(): String =
    if (this > 0) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")
        val ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        formatter.format(ldt)
    } else {
        ""
    }


