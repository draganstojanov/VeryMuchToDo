package com.andraganoid.verymuchtodo.kmodel


data class Message(
        val text: String = "",
        val timestamp: Long = 0,
        var from: User = User(),
        val id: String = "") {
    var isMyMsg: Boolean = false
}

