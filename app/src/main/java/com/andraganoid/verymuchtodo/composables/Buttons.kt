package com.andraganoid.verymuchtodo.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andraganoid.verymuchtodo.ui.theme.ColorSecondaryLite
import com.andraganoid.verymuchtodo.ui.theme.ColorText100
import com.andraganoid.verymuchtodo.ui.theme.ColorText25
import com.andraganoid.verymuchtodo.ui.theme.ItemButtonStyle
import com.andraganoid.verymuchtodo.ui.theme.SmallButtonStyle


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
            style = ItemButtonStyle
        )
    }
}

@Composable
fun SmallButton(
    content: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(120.dp)
            .clip(RoundedCornerShape(50))
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = ColorSecondaryLite)
    ) {
        Text(text = content.uppercase(), style = SmallButtonStyle)
    }
}

@Composable
fun CloseButton(onCLose: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(onClick = onCLose) {
            Icon(
                Icons.Filled.Close,
                "Close",
                tint = Color.White
            )
        }
    }
}