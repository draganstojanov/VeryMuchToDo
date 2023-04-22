package com.andraganoid.verymuchtodo.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryVariantLite
import com.andraganoid.verymuchtodo.ui.theme.ColorText50
import com.andraganoid.verymuchtodo.ui.theme.ColorText75
import com.andraganoid.verymuchtodo.ui.theme.InputStyle


@Composable
fun InputText(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    var input by remember { mutableStateOf(TextFieldValue(value)) }
    TextField(
        value = input,
        onValueChange = {
            input = it
            onValueChanged.invoke(it.text)
        },
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        textStyle = InputStyle,
        singleLine = false,
        maxLines = 6,
        label = {
            Text(
                color = ColorText75,
                text = label
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = ColorPrimaryLite,
            backgroundColor = ColorPrimaryVariantLite,
            placeholderColor = ColorText50
        )
    )

}













@Composable
fun TopModal() {

    var visibility by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = visibility
        ) {

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(color = Color.Red)
                    .fillMaxWidth()
            )
            {
                Expanded() // Content to be collapsed or displayed
            }

        }
        Text("Click to expand or collapse", modifier = Modifier
            .fillMaxWidth()
            .clickable {
                visibility = !visibility
            }
        )
    }


//    var expanded by remember { mutableStateOf(false) }
//    val density = LocalDensity.current
//    Surface(
//        color = MaterialTheme.colors.primary,
//        onClick = { expanded = !expanded }
//    ) {

//       // var enabled by remember { mutableStateOf(true) }
//
//        val alpha: Float by animateFloatAsState(if (expanded) 1f else 0.5f)
//        Box(
//            Modifier.fillMaxSize()
//                .
//                .graphicsLayer(alpha = alpha)
//                .background(Color.Red)
//        )


}
//        AnimatedVisibility(
//            visible = expanded,
//            enter =
//            slideInVertically {
//                 Slide in from 40 dp from the top.
//                with(density) { 0.dp.roundToPx() }
//            } +
//            expandVertically(
//                // Expand from the top.
//                expandFrom = Alignment.Top
//            ) + fadeIn(
//                // Fade in with the initial alpha of 0.3f.
//                initialAlpha = 0.3f
//            ),
//            exit = slideOutVertically()
//           +shrinkVertically()
//              + fadeOut()
//        ) {
//           Text("Hello", Modifier.fillMaxWidth().height(300.dp))
//        }
//        AnimatedContent(
//            targetState = expanded,
//            transitionSpec = {
//                fadeIn(animationSpec = tween(150, 150)) with
//                        fadeOut(animationSpec = tween(150)) using
//                        SizeTransform { initialSize, targetSize ->
//                            if (targetState) {
//                                keyframes {
//                                    // Expand horizontally first.
//                                    IntSize(targetSize.width, initialSize.height) at 150
//                                    durationMillis = 300
//                                }
//                            } else {
//                                keyframes {
//                                    // Shrink vertically first.
//                                    IntSize(initialSize.width, targetSize.height) at 150
//                                    durationMillis = 300
//                                }
//                            }
//                        }
//            }
//        ) { targetExpanded ->
//            if (targetExpanded) {
//                Expanded()
//            } else {
//                ContentIcon()
//            }
//        }
//    }
//}

@Composable
fun Expanded() {
    repeat(3) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            ) {
            Text(
                text = "TOP MODAL",
                modifier = Modifier
            )
        }
    }


}

@Composable
fun ContentIcon() {
    // Image(painter = painterResource(id = R.drawable.ic_calculate), contentDescription = "")
}