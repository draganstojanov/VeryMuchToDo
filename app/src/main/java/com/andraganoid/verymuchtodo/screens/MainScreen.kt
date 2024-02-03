package com.andraganoid.verymuchtodo.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.andraganoid.verymuchtodo.model.state.AuthState
import com.andraganoid.verymuchtodo.util.navigation.NavScreens
import com.andraganoid.verymuchtodo.viewModel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    when (val authState = viewModel.authState.value) {
        is AuthState.Success -> {
            viewModel.setFirestoreListeners()
            navController.navigate(NavScreens.StackScreen.name)
        }

        //  is AuthState.Cancelled -> snackbarState.value = CANCELLED
        //  is AuthState.Error -> snackbarState.value = "$ERROR_PLACEHOLDER: ${authState.errorMsg}"
        else -> {}
    }

}
