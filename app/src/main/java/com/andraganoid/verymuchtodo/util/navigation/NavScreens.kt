package com.andraganoid.verymuchtodo.util.navigation

import kotlinx.serialization.Serializable

//enum class NavScreens {
//    MainScreen,
//    StackScreen,
//    ToDoListScreen
//}

sealed interface Screen {

@Serializable
    object MainScreen

    @Serializable
    object StackScreen

    @Serializable
    data class TodoListScreen(
        val stackId: String?
    )

}

