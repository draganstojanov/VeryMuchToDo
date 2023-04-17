package com.andraganoid.verymuchtodo.model

import com.andraganoid.verymuchtodo.util.*

class Document(value: Any?) {

    var collection: String = ""
    var name: String = ""
    var values: Map<String?, Any?> = mapOf()

    init {
        todoList(value as TodoStack)
    }

    private fun todoList(todoStack: TodoStack) {
        collection = COL_LIST
        name = todoStack.id
        values = mapOf(
            KEY_TITLE to todoStack.title,
            KEY_ID to todoStack.id,
            KEY_DESCRIPTION to todoStack.description,
            KEY_COMPLETED to todoStack.isCompleted(),
            KEY_USER_NAME to todoStack.userName,
            KEY_ITEM_LIST to todoStack.itemList,
            KEY_TIMESTAMP to todoStack.timestamp
        )
    }

}