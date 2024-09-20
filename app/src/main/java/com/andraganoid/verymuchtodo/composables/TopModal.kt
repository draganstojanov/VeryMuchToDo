package com.andraganoid.verymuchtodo.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryVariantLite
import com.andraganoid.verymuchtodo.ui.theme.ColorText50
import com.andraganoid.verymuchtodo.ui.theme.ColorText75
import com.andraganoid.verymuchtodo.ui.theme.InputStyle
import com.andraganoid.verymuchtodo.util.noRippleClickable


@Composable
fun TopModal(
    editorState: Boolean,
    editorStateUpdate: (Boolean) -> Unit,
    content: String?,
    description: String?,
    editorItemUpdate: (String?, String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (editorState) Color.Black.copy(alpha = .33f) else Color.Transparent)
    ) {

        var titleInput by remember { mutableStateOf("") }
        var descInput by remember { mutableStateOf("") }

        titleInput = content ?: ""
        descInput = description ?: ""

        AnimatedVisibility(
            visible = editorState,
            enter = expandVertically(animationSpec = tween(durationMillis = 500)),
            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .noRippleClickable { }
            )
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(color = ColorPrimaryLite)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            {
                CloseButton {
                    editorStateUpdate(false)
                }

                InputText(
                    label = stringResource(id = R.string.title),
                    value = titleInput,
                    onValueChanged = { titleInput = it })

                InputText(
                    label = stringResource(id = R.string.description),
                    value = descInput,
                    onValueChanged = { descInput = it })

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.BottomCenter

                ) {
                    SmallButton(stringResource(id = R.string.save)) {
                        editorItemUpdate(titleInput, descInput)
                        editorStateUpdate(false)
                    }
                }
            }
        }
    }

}


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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = ColorPrimaryLite,
            backgroundColor = ColorPrimaryVariantLite,
            placeholderColor = ColorText50
        )
    )

}


//@Composable
//fun TopModal() {
//
//    var visibility by remember { mutableStateOf(true) }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        AnimatedVisibility(
//            visible = visibility
//        ) {
//
//            Column(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
//                    .background(color = Color.Red)
//                    .fillMaxWidth()
//            )
//            {
//                Expanded() // Content to be collapsed or displayed
//            }
//
//        }
//        Text("Click to expand or collapse", modifier = Modifier
//            .fillMaxWidth()
//            .clickable {
//                visibility = !visibility
//            }
//        )
//    }


//@Composable
//fun Expanded() {
//    repeat(3) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//
//            ) {
//            Text(
//                text = "TOP MODAL",
//                modifier = Modifier
//            )
//        }
//    }
//
//
//}
