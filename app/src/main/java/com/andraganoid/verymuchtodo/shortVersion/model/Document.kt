package com.andraganoid.verymuchtodo.shortVersion.model

import com.andraganoid.verymuchtodo.shortVersion.util.COL_LIST

class Document(value: Any?) {

    var collection: String = ""
    var name: String = ""
    var values: Map<String?, Any?> = mapOf()

    init {
        todoList(value as TodoList)
    }

    private fun todoList(todoList: TodoList) {
        collection = COL_LIST
        name = todoList.id
        values = mapOf(
            "title" to todoList.title,
            "id" to todoList.id,
            "description" to todoList.description,
            "completed" to todoList.completed,
            "userName" to todoList.userName,
            "todoList" to todoList.todoList,
            "timestamp" to todoList.timestamp
        )
    }


}