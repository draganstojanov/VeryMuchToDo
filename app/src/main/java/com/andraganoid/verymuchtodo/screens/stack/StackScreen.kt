package com.andraganoid.verymuchtodo.screens.stack

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.composables.CloseButton
import com.andraganoid.verymuchtodo.composables.CustomTopAppBar
import com.andraganoid.verymuchtodo.composables.SmallButton
import com.andraganoid.verymuchtodo.composables.showToast
import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryVariantLite
import com.andraganoid.verymuchtodo.ui.theme.ColorText50
import com.andraganoid.verymuchtodo.ui.theme.ColorText75
import com.andraganoid.verymuchtodo.ui.theme.InputStyle
import com.andraganoid.verymuchtodo.util.navigation.NavScreens
import com.andraganoid.verymuchtodo.util.noRippleClickable
import com.andraganoid.verymuchtodo.viewModel.StackViewModel


@Composable
fun StackScreen(
    navController: NavHostController,
    viewModel: StackViewModel
) {
    val context = LocalContext.current
    val stackState by viewModel.getSnapshotState().collectAsState(initial = null)
    val stackListState: MutableState<List<TodoStack?>> = remember { mutableStateOf(listOf()) }

    var editorState by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = stackState) {
        when (stackState) {
            is StackState.Stack -> {
                val stackList = (stackState as StackState.Stack).stack
                stackListState.value = stackList.sortedByDescending { it?.timestamp }
            }

            is StackState.Error -> showToast(context, (stackState as StackState.Error).errorMsg)//todo -> bottom msg
            null -> {}
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CustomTopAppBar(
                title = stringResource(id = R.string.todo),
                navController = navController,
                hasBackButton = false,
                hasCalcButton = true,
                hasSettingsButton = true
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = stackListState.value) { stack ->
                    StackItem(
                        item = stack,
                        onItemSelected = {
                            if (!editorState) {
                                viewModel.selectedListId = it
                                navController.navigate("${NavScreens.ToDoListScreen.name}/${it}")
                            }
                        },
                        onEditorCLick = {
                            if (!editorState) {
                                editorState = true
                            }
                        },
                        onDeleteCLick = {}
                    )
                }
                item { Spacer(modifier = Modifier.size(72.dp)) }
            }
        }
        Box(//todo common DO
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                SmallButton(stringResource(id = R.string.new_list)) {
                    editorState = true
                }
                SmallButton(stringResource(id = R.string.clear)) {}
            }
        }
        Column(//TODO COMMON DO
            modifier = Modifier
                .fillMaxSize()
                .background(if (editorState) Color.Black.copy(alpha = .33f) else Color.Transparent)
        ) {

            var resetEdit by remember { mutableStateOf(true) }

            var titleInput by remember { mutableStateOf("") }
            var descInput by remember { mutableStateOf("") }

            AnimatedVisibility(
                visible = editorState,
                enter = expandVertically(animationSpec = tween(durationMillis = 1000)),
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
                    CloseButton { editorState = false }

                    //   if (resetEdit) {
                    InputText(XXX
                        label = stringResource(id = R.string.title),
                        value = titleInput,
                        onValueChanged = {
                            titleInput = it
                        })

                    InputText(
                        label = stringResource(id = R.string.description),
                        value = descInput,
                        onValueChanged = {
                            descInput = it
                        })

                    //        resetEdit=false
                    //    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.BottomCenter

                    ) {
                        SmallButton(stringResource(id = R.string.save)) {
                            // viewModel.saveXXX()
                            // resetEdit=true
                            titleInput = "EEE"
                        }
                    }


                }

            }
        }


    }
}

@Composable
fun InputText(//TODO COMMON
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





