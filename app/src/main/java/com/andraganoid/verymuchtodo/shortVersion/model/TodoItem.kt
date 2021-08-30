package com.andraganoid.verymuchtodo.shortVersion.model

data class TodoItem(
    val content: String? = "",
    val description: String? = "",
    val completed: Boolean? = false,
    var timestamp: Long? = 0,
    //    val user: User? = null,
    var userName: String? = "",
    val id: String? = "",
    var edited: Boolean = false
)
