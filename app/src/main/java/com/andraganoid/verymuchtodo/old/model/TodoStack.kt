package com.andraganoid.verymuchtodo.old.model

data class TodoStack(
    var title: String? = "",
    var description: String? = "",
    var itemList: ArrayList<TodoItem> = arrayListOf(),
    var timestamp: Long? = 0,
    var userName: String? = "",
    var id: String = ""
)

//fun TodoList.isCompleted(): Boolean {
//    if (itemList.isEmpty()) {
//        return true
//    } else {
//        itemList.forEach {
//            if (!it.completed) {
//                return false
//            }
//        }
//        return true
//    }
//}

fun TodoStack.isCompleted(): Boolean = itemList.count { it.completed } == itemList.size






