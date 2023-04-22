package com.andraganoid.verymuchtodo.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.andraganoid.verymuchtodo.ui.theme.SmallButtonStyle



@Composable
fun CloseButton(onClose: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(onClick = onClose) {
            Icon(
                Icons.Filled.Close,
                "Close",
                tint = Color.White
            )
        }
    }
}

@Composable
fun SmallButton(
    label: String,
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
        Text(text = label.uppercase(), style = SmallButtonStyle)
    }
}