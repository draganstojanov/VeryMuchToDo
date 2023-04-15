package com.andraganoid.verymuchtodo.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andraganoid.verymuchtodo.ui.theme.ButtonStyle
import com.andraganoid.verymuchtodo.ui.theme.ColorText100
import com.andraganoid.verymuchtodo.ui.theme.ColorText25


@Composable
fun ItemButton(
    content: String,
    isEnabled: Boolean,
    onCLick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .wrapContentWidth()
            .height(24.dp),
        elevation = null,
        enabled = isEnabled,
        onClick = onCLick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = ColorText100,
            disabledContentColor = ColorText25
        ),
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, color = if (isEnabled) ColorText100 else ColorText25),
    ) {
        Text(
            text = content,
            style = ButtonStyle
        )
    }
}