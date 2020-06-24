package com.andraganoid.verymuchtodo.kmodel
//TODO
data class Todo(val content: String = "",
                val description: String = "",
                val completed: Boolean = false,
                val timestamp: Long = 0,
                val user: User? = null,
                val id: String = "",
                val group: Group? = null
)




//Todo creator can delete before completed, others only on completed
//Todo TodoItemGroup(id, color, name, description)