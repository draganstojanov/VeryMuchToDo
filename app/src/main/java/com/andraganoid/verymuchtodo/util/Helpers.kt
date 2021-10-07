package com.andraganoid.verymuchtodo.util

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.main.MainActivity
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialog
import com.andraganoid.verymuchtodo.ui.msgDialog.MessageDialogData

fun MainActivity.bottomToast(msg: Any?) {
    MessageDialog().apply {
        arguments = bundleOf(ARGS_DIALOG_DATA to MessageDialogData(toast = msg.toString()))
        show(supportFragmentManager, MessageDialog::class.simpleName)
    }
}

fun Fragment.bottomToast(msg: Any?) {
    messageDialog(MessageDialogData(toast = msg.toString()))
}

fun Fragment.messageDialog(dialogData: MessageDialogData) {
    findNavController().navigate(R.id.messageDialog, bundleOf(ARGS_DIALOG_DATA to dialogData))
}


