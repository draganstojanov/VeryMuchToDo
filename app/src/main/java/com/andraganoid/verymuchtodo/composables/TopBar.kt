package com.andraganoid.verymuchtodo.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.andraganoid.verymuchtodo.ui.theme.TopTitle

@Composable
fun CustomTopAppBar(
    title: String?,
    hasBackButton: Boolean = true,
    hasCalcButton: Boolean = true,
    hasSettingsButton: Boolean = true,
    navController: NavController
) {
    TopAppBar(
        title = { Text(title.toString(), style = TopTitle) },
        actions = {
            if (hasCalcButton) {
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Calculate, "Calculate")
                }
            }
            if (hasSettingsButton) {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Filled.Settings, "Settings",
                    )
                }
            }
        },
        navigationIcon =
        if (hasBackButton) {
            {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, "Back")
                }
            }
        } else null
    )
}