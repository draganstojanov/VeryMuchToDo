package com.andraganoid.verymuchtodo.model.state

import com.andraganoid.verymuchtodo.model.TodoList

sealed class StackState {
    data class Error(val errorMsg: String?) : StackState()
    data class Stack(val stack: ArrayList<TodoList?>) : StackState()
}