package com.andraganoid.verymuchtodo.shortVersion.repository

import com.andraganoid.verymuchtodo.shortVersion.model.TodoList

sealed class SnapshotState {
    data class Error(val errorMsg: String?) : SnapshotState()
    data class TodoLists(val todoLists: ArrayList<TodoList?>) : SnapshotState()
    object Unchecked : SnapshotState()
}