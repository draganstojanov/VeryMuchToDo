package com.andraganoid.verymuchtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.andraganoid.verymuchtodo.ui.theme.ToDoComposeTheme
import com.andraganoid.verymuchtodo.util.navigation.ToDoNavigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                val color = MaterialTheme.colors.primary
                window.apply {
                    statusBarColor = color.toArgb()
                    navigationBarColor = color.toArgb()
                    WindowCompat.getInsetsController(this, this.decorView).apply {
                        isAppearanceLightStatusBars = false
                        isAppearanceLightNavigationBars = false
                    }
                }
            }

            ToDoNavigation()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoComposeTheme {

    }
}