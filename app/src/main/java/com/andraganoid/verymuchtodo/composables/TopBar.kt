package com.andraganoid.verymuchtodo.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.andraganoid.verymuchtodo.ui.theme.ColorIconActive
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
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
        backgroundColor = ColorPrimaryLite,
        contentColor = ColorIconActive,
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
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            }
        } else null
    )
}