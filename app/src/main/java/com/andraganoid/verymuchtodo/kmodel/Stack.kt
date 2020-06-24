package com.andraganoid.verymuchtodo.kmodel

//TODO
data class Stack(val title: String = "",
                 val description: String = "",
                 val completed: Boolean = false,
                 val todoList: List<Todo> = arrayListOf<Todo>(),
                 val timestamp: Long = 0,
                 val user: User? = null,
                 val id: String = "")