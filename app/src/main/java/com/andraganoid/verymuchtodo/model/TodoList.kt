package com.andraganoid.verymuchtodo.model

data class TodoList(
    var title: String? = "",
    var description: String? = "",
    var itemList: ArrayList<TodoItem> = arrayListOf(),
    var timestamp: Long? = 0,
    var userName: String? = "",
    var id: String = ""
)

fun TodoList.isCompleted(): Boolean {
    if (itemList.isEmpty()) {
        return true
    } else {
        itemList.forEach {
            if (!it.completed) {
                return false
            }
        }
        return true
    }
}



