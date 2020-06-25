package com.andraganoid.verymuchtodo.kmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//TODO

@Entity(tableName = "stack_table")
data class Stack(
        val title: String = "",
        val description: String = "",
        val completed: Boolean = false,
        val todoList: List<Todo> = arrayListOf<Todo>(),
        val timestamp: Long = 0,
        val user: User? = null,
        @PrimaryKey
        val id: String = ""
) : Serializable