package com.andraganoid.verymuchtodo.old.model

import com.andraganoid.verymuchtodo.util.*

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
            KEY_TITLE to todoList.title,
            KEY_ID to todoList.id,
            KEY_DESCRIPTION to todoList.description,
            KEY_COMPLETED to todoList.isCompleted(),
            KEY_USER_NAME to todoList.userName,
            KEY_ITEM_LIST to todoList.itemList,
            KEY_TIMESTAMP to todoList.timestamp
        )
    }

}