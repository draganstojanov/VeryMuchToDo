package com.andraganoid.verymuchtodo.ui.msgDialog

data class MessageDialogData(
        val title: String = "",
        val desc: String = "",
        val btnLeftText: String = "",
        val btnMidText: String = "",
        val btnRightText: String = "",
        val btnLeft: (() -> Unit)? = {},
        val btnMid: (() -> Unit)? = {},
        val btnRight: (() -> Unit)? = {},
        val toast: String = "",
)