package com.andraganoid.verymuchtodo.ui.msgDialog

import java.io.Serializable

data class MessageDialogData(
    val title: String = "",
    val desc: String = "",
    val btnLeftText: String = "",
    val btnMidText: String = "",
    val btnRightText: String = "",
    val btnLeft: (() -> Unit)? = null,
    val btnMid: (() -> Unit)? = null,
    val btnRight: (() -> Unit)? = null,
    val toast: String = "",
) : Serializable