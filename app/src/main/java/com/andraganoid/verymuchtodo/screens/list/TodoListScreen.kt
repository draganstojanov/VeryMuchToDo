package com.andraganoid.verymuchtodo.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.composables.CustomTopAppBar
import com.andraganoid.verymuchtodo.composables.ItemButton
import com.andraganoid.verymuchtodo.composables.SmallButton
import com.andraganoid.verymuchtodo.composables.showToast
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.model.state.StackState
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryVariantLite
import com.andraganoid.verymuchtodo.ui.theme.ColorTaskDone
import com.andraganoid.verymuchtodo.ui.theme.Desc
import com.andraganoid.verymuchtodo.ui.theme.Info
import com.andraganoid.verymuchtodo.ui.theme.Title
import com.andraganoid.verymuchtodo.util.getFormattedShortDateAndTimeLocale
import com.andraganoid.verymuchtodo.viewModel.ListViewModel

@Composable
fun ToDoListScreen(
    navController: NavHostController,
    viewModel: ListViewModel,
    stackId: String?
) {
    val context = LocalContext.current
    val stackState by viewModel.getSnapshotState().collectAsState(initial = null)
    val todoListState: MutableState<List<TodoItem?>> = remember { mutableStateOf(listOf()) }
    var titleState by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = stackState) {
        when (stackState) {
            is StackState.Stack -> {
                val stackList = (stackState as StackState.Stack).stack
                val stack = stackList.first { it?.id.equals(stackId) }
                todoListState.value = stack?.itemList ?: emptyList()
                titleState = stack?.title
            }

            is StackState.Error -> showToast(context, (stackState as StackState.Error).errorMsg)
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
                    TodoListItem(item, viewModel)
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
                SmallButton(stringResource(id = R.string.new_item)) {}
                SmallButton(stringResource(id = R.string.clear)) {}
            }
        }


        //  TopModal()
    }
}



