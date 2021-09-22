package com.andraganoid.verymuchtodo.model

data class TodoList(
    var title: String? = "",
    var description: String? = "",
    var itemList: ArrayList<TodoItem> = arrayListOf<TodoItem>(),
    var timestamp: Long? = 0,
    var userName: String? = "",
    var id: String = "",
) {
    val completed: Boolean
        get() {
            if (itemList.isEmpty()) {
                return true
            } else {
                itemList.forEach {
                    if (!it.completed) {
                        return false
                    }
                }
            }
            return true
        }

}
