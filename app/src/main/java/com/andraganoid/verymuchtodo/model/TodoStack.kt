package com.andraganoid.verymuchtodo.model

import kotlinx.serialization.Serializable

@Serializable
data class TodoStack(
    var title: String? = "",
    var description: String? = "",
    var itemList: ArrayList<TodoList> = arrayListOf(),
    var timestamp: Long? = 0,
    var userName: String? = "",
    var id: String = ""
)

fun TodoStack.isCompleted(): Boolean = itemList.count { it.completed } == itemList.size






