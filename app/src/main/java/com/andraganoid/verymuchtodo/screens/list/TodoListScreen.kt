package com.andraganoid.verymuchtodo.screens.list

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.composables.CloseButton
import com.andraganoid.verymuchtodo.composables.CustomTopAppBar
import com.andraganoid.verymuchtodo.composables.InputText
import com.andraganoid.verymuchtodo.composables.SmallButton
import com.andraganoid.verymuchtodo.composables.showToast
import com.andraganoid.verymuchtodo.model.TodoList
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
import com.andraganoid.verymuchtodo.util.noRippleClickable
import com.andraganoid.verymuchtodo.viewModel.ListViewModel

@Composable
fun ToDoListScreen(
    navController: NavHostController,
    viewModel: ListViewModel,
    stackId: String?
) {
    val context = LocalContext.current
    val stackState = viewModel.getSnapshotState()
    val todoListState: MutableState<List<TodoList?>> = remember { mutableStateOf(listOf()) }
    var titleState by remember { mutableStateOf<String?>(null) }
    var editorState by remember { mutableStateOf(false) }
    var editorItem: TodoList? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = stackState) {

        when (val stackStateValue = stackState.value) {
            is StackState.Stack -> {
                val stackList = stackStateValue.stack
                val stack = stackList.firstOrNull { it?.id.equals(stackId) }
                viewModel.currentStack = stack?.copy()
                todoListState.value = stack?.itemList?.toList() ?: emptyList()
                titleState = stack?.title
            }

            is StackState.Error -> showToast(context, stackStateValue.errorMsg)

            null -> {}
        }
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CustomTopAppBar(
                title = titleState,
                navController = navController,
                hasBackButton = true,
                hasCalcButton = true,
                hasSettingsButton = true
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = todoListState.value) { item ->
                    TodoListItem(
                        item = item,
                        onEditorCLick = {
                            if (!editorState) {
                                editorItem = item
                                editorState = true
                            }
                        },
                        onDeleteCLick = {},
                    )
                }
                item { Spacer(modifier = Modifier.size(72.dp)) }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                SmallButton(stringResource(id = R.string.new_item)) {
                    editorState = true
                    editorItem = TodoList()
                }
                SmallButton(stringResource(id = R.string.clear)) {}
            }
        }

        Column(//TODO COMMON TOPMODAL
            modifier = Modifier
                .fillMaxSize()
                .background(if (editorState) Color.Black.copy(alpha = .33f) else Color.Transparent)
        ) {

            var titleInput by remember { mutableStateOf("") }
            var descInput by remember { mutableStateOf("") }

            titleInput = editorItem?.content ?: ""
            descInput = editorItem?.description ?: ""

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
                    CloseButton { editorState = false }

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
                            editorItem?.apply {
                                content = titleInput
                                description = descInput
                            }

                            viewModel.updateItem(editorItem)
                            editorState = false
                        }
                    }
                }
            }
        }

    }
}



