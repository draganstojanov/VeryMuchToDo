package com.andraganoid.verymuchtodo.model

data class TodoList(
    var title: String? = "",
    var description: String? = "",
    var completed: Boolean? = false,
    var todoList: List<TodoItem> = arrayListOf<TodoItem>(),
    var timestamp: Long? = 0,
//    val user: User? = null,
    var userName: String? = "",
    var id: String = "",
    var edited: Boolean = false
)
