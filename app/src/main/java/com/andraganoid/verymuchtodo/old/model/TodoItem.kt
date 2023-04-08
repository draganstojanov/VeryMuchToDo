package com.andraganoid.verymuchtodo.old.model

data class TodoItem(
    var content: String? = "",
    var description: String? = "",
    var completed: Boolean = false,
    var timestamp: Long? = 0,
    var userName: String? = "",
    var id: String? = "",
    var edited: Boolean = false,
)
