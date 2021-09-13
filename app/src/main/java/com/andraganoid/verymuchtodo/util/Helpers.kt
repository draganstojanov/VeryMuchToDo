package com.andraganoid.verymuchtodo.util

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.andraganoid.verymuchtodo.main.MainActivity
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialog
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialogData

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