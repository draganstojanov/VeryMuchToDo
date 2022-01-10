package com.andraganoid.verymuchtodo.model

data class TodoItem(
    var content: String? = "",
    var description: String? = "",
    var completed: Boolean = false,
    var timestamp: Long? = 0,
    var userName: String? = "",
    var id: String? = "",
    var edited: Boolean = false,
    var sortingTimestamp: Long? = Long.MIN_VALUE
)
