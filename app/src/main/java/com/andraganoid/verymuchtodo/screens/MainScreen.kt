package com.andraganoid.verymuchtodo.screens

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.andraganoid.verymuchtodo.old.model.state.AuthState
import com.andraganoid.verymuchtodo.util.CANCELLED
import com.andraganoid.verymuchtodo.util.ERROR_PLACEHOLDER
import com.andraganoid.verymuchtodo.util.navigation.NavScreens
import com.andraganoid.verymuchtodo.viewModel.MainViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackbarState = remember { mutableStateOf<String?>(null) }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        LaunchedEffect(key1 = AuthState.Unchecked) {

            when ( val authState = viewModel.authState.value) {
                is AuthState.Success -> {
                    viewModel.setFirestoreListeners()
                    navController.navigate(NavScreens.StackScreen.name)
                }
                is AuthState.Cancelled -> snackbarState.value = CANCELLED
                is AuthState.Error -> snackbarState.value = "$ERROR_PLACEHOLDER: ${(authState as AuthState.Error).errorMsg}"
                else -> {}
            }

            if (snackbarState.value != null) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = snackbarState.value.toString(),
                        actionLabel = "Action",
                        duration = SnackbarDuration.Short,
                    )
                    snackbarState.value = null
                }

            }
        }


    }
}
