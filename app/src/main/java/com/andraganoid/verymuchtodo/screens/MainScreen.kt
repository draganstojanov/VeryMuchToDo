package com.andraganoid.verymuchtodo.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.andraganoid.verymuchtodo.composables.showToast
import com.andraganoid.verymuchtodo.model.state.AuthState
import com.andraganoid.verymuchtodo.util.navigation.NavScreens
import com.andraganoid.verymuchtodo.viewModel.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
  context: Context = LocalContext.current
) {
      //  val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
      //  val snackbarState = remember { mutableStateOf<String?>(null) }

        Scaffold(//snackbarHost = { SnackbarHost(snackbarHostState) }
        ) {
        //    LaunchedEffect(key1 = AuthState.Unchecked) {//todo ddd

                when (val authState = viewModel.authState.value) {
                    is AuthState.Success -> {
                        showToast(context,"AAAAA")
                        viewModel.setFirestoreListeners()
                        navController.navigate(NavScreens.StackScreen.name)
                    }

                  //  is AuthState.Cancelled -> snackbarState.value = CANCELLED
                  //  is AuthState.Error -> snackbarState.value = "$ERROR_PLACEHOLDER: ${authState.errorMsg}"
                    else -> {}
                }

//                if (snackbarState.value != null) {
//                    scope.launch {
//                        snackbarHostState.showSnackbar(
//                            message = snackbarState.value.toString(),
//                            actionLabel = "Action",
//                            duration = SnackbarDuration.Short,
//                        )
//                        snackbarState.value = null
//                    }
//
//                }
          //  }


        }
}
