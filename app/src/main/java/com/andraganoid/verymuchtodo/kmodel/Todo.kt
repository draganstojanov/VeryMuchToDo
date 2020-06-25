package com.andraganoid.verymuchtodo.kmodel

import java.io.Serializable

//TODO
data class Todo(
        val content: String = "",
        val description: String = "",
        val completed: Boolean = false,
        val timestamp: Long = 0,
        val user: User? = null,
        val id: String = "",
        val group: Group? = null
) : Serializable
