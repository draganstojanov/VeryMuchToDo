package com.andraganoid.verymuchtodo.util.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andraganoid.verymuchtodo.screens.MainScreen
import com.andraganoid.verymuchtodo.screens.list.ToDoListScreen
import com.andraganoid.verymuchtodo.screens.stack.StackScreen
import com.andraganoid.verymuchtodo.util.ARG_SELECTED_STACK_ID
import org.koin.androidx.compose.koinViewModel

@Composable
fun ToDoNavigation() {
    val navController = rememberNavController()
    //   val todoViewModel= koinViewModel<StackViewModel>()
    NavHost(
        navController = navController,
        startDestination = NavScreens.MainScreen.name
    ) {
        composable(NavScreens.MainScreen.name) {
            MainScreen(navController, koinViewModel())
        }
        composable(NavScreens.StackScreen.name) {
            StackScreen(navController, koinViewModel())
        }
        composable(
            "${NavScreens.ToDoListScreen.name}/{${ARG_SELECTED_STACK_ID}}",
            arguments = listOf(navArgument(ARG_SELECTED_STACK_ID) { type = NavType.StringType })
        ) {
            ToDoListScreen(
                navController,
                koinViewModel(),
                it.arguments?.getString(ARG_SELECTED_STACK_ID)
            )
        }


//        composable(
//            "${NavScreens.CountryListScreen.name}/{$ARG_FILTERED_LIST}/{$ARG_TITLE}",
//            arguments = listOf(
//                navArgument(ARG_FILTERED_LIST) { type = NavType.StringType },
//                navArgument(ARG_TITLE) { type = NavType.StringType }
//            )
//        ) {
//            NavScreens.CountryListScreen(
//                navController,
//                it.arguments?.getString(ARG_TITLE),
//                hiltViewModel()
//            )
//        }
//
//        composable(
//            "${NavScreens.CountryDetailsScreen.name}/{$ARG_COUNTRY_ID}",
//            arguments = listOf(navArgument(ARG_COUNTRY_ID) { type = NavType.IntType })
//        ) {
//            NavScreens.CountryDetailsScreen(navController, hiltViewModel())
//        }
//
//        composable(
//            "${NavScreens.MapScreen.name}/{$ARG_TITLE}/{$ARG_URL}",
//            arguments = listOf(
//                navArgument(ARG_TITLE) { type = NavType.StringType },
//                navArgument(ARG_URL) { type = NavType.StringType }
//            )
//        ) {
//            NavScreens.MapScreen(
//                navController,
//                URLDecoder.decode(it.arguments?.getString(ARG_TITLE), "UTF8"),
//                URLDecoder.decode(it.arguments?.getString(ARG_URL), "UTF8")
//            )
//        }
    }
}

