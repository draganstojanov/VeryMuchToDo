package com.andraganoid.verymuchtodo.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite


@Composable
fun ItemEditor(
    expanded: Boolean,
    onCollapse: () -> Unit
) {

  val isExpanded by remember { mutableStateOf(expanded) }

    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(color = ColorPrimaryLite)
                    .fillMaxWidth().height(350.dp)
            )
            {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(
                        onClick = onCollapse
                    )
                    // { isExpanded = false})
                    {
                        Icon(
                            Icons.Filled.Close,
                            "Close",
                            tint = Color.White
                        )
                    }
                }

            }
        }
    }
}
