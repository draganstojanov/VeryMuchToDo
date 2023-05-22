package com.andraganoid.verymuchtodo.model.state

import com.andraganoid.verymuchtodo.model.TodoStack

sealed class StackState {
    data class Error(val errorMsg: String?) : StackState()
    data class Stack(val stack: List<TodoStack?>) : StackState()
}