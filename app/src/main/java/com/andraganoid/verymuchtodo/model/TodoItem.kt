package com.andraganoid.verymuchtodo.model

data class TodoItem(
    var content: String? = "",
    var description: String? = "",
    var completed: Boolean = false,
    var timestamp: Long? = 0,
    //    val user: User? = null,
    var userName: String? = "",
    val id: String? = "",
    var edited: Boolean = false
)
