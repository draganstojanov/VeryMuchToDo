package com.andraganoid.verymuchtodo.kmodel

import java.io.Serializable


data class Message(
        val text: String = "",
        val timestamp: Long = 0,
        var from: User = User(),
        val id: String = "") : Serializable {
    var isMyMsg: Boolean = false
}

