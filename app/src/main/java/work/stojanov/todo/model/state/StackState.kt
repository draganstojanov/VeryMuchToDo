package work.stojanov.todo.model.state

import work.stojanov.todo.model.TodoList

sealed class StackState {
    data class Error(val errorMsg: String?) : StackState()
    data class Stack(val stack: ArrayList<TodoList?>) : StackState()
}