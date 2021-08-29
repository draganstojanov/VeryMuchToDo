package com.andraganoid.verymuchtodo.shortVersion.model

import com.andraganoid.verymuchtodo.kmodel.User

data class TodoItem(
    val content: String? = "",
    val description: String? = "",
    val completed: Boolean? = false,
    val timestamp: Long? = 0,
    val user: User? = null,
    val id: String? = ""
)
