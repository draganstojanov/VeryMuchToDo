package com.andraganoid.verymuchtodo.shortVersion.model

import com.andraganoid.verymuchtodo.kmodel.User

data class TodoList(
    val title: String? = "",
    val description: String? = "",
    val completed: Boolean? = false,
    val todoList: List<TodoItem> = arrayListOf<TodoItem>(),
    val timestamp: Long? = 0,
    val user: User? = null,
    val id: String? = ""//todo ???
)
