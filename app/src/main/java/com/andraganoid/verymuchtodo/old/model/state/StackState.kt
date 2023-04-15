package com.andraganoid.verymuchtodo.old.model.state

import com.andraganoid.verymuchtodo.old.model.TodoStack

sealed class StackState {
    data class Error(val errorMsg: String?) : StackState()
    data class Stack(val stack: ArrayList<TodoStack?>) : StackState()
}