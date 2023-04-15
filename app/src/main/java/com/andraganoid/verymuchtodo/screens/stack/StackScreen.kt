package com.andraganoid.verymuchtodo.screens.stack

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
import com.andraganoid.verymuchtodo.composables.showToast
import com.andraganoid.verymuchtodo.old.model.TodoStack
import com.andraganoid.verymuchtodo.old.model.isCompleted
import com.andraganoid.verymuchtodo.old.model.state.StackState
import com.andraganoid.verymuchtodo.old.util.getFormattedDateShort
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryVariantLite
import com.andraganoid.verymuchtodo.ui.theme.ColorTaskDone
import com.andraganoid.verymuchtodo.ui.theme.Desc
import com.andraganoid.verymuchtodo.ui.theme.Info
import com.andraganoid.verymuchtodo.ui.theme.Title
import com.andraganoid.verymuchtodo.viewModel.StackViewModel


@Composable
fun StackScreen(
    navController: NavHostController,
    viewModel: StackViewModel
) {
    val context = LocalContext.current
    val stackState by viewModel.getSnapshotState().collectAsState(initial = null)
    val stackListState: MutableState<List<TodoStack?>> = remember { mutableStateOf(listOf()) }

    LaunchedEffect(key1 = stackState) {
        when (stackState) {
            is StackState.Stack -> {
                val stackList = (stackState as StackState.Stack).stack
                stackListState.value = stackList.sortedByDescending { it?.timestamp }

//            binding.clearList.isVisible = viewModel.checkClearVisibilityStack()
            }

            is StackState.Error -> showToast(context, (stackState as StackState.Error).errorMsg)
            null -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CustomTopAppBar(
                title = stringResource(id = R.string.todo),
                navController = navController,
                hasBackButton = false,
                hasCalcButton = true,
                hasSettingsButton = true
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = stackListState.value) { item ->
                    StackItem(item, viewModel)
                }
                item { Spacer(modifier = Modifier.size(4.dp)) }

            }

        }


        //   TopModal()
    }
}

@Composable
fun StackItem(stack: TodoStack?, viewModel: StackViewModel) {

    val isCompleted = stack?.isCompleted() == true

    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.cornerRadius)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 4.dp, end = 4.dp),
        backgroundColor = if (isCompleted) ColorTaskDone else ColorPrimaryLite
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(ColorPrimaryVariantLite)
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = stack?.title.toString(), style = Title
            )

            if (!stack?.description.isNullOrEmpty()) {
                Text(
                    text = stack?.description.toString(),
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    style = Desc
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(32.dp),


                ) {
                Row(
                    modifier = Modifier.padding(start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ItemButton(
                        content = stringResource(id = R.string.delete).uppercase(),
                        isEnabled = isCompleted,
                        onCLick = {}
                    )

                    ItemButton(
                        content = stringResource(id = R.string.edit).uppercase(),
                        isEnabled = true,
                        onCLick = {}
                    )


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = stack?.userName.toString(),
                            style = Info
                        )
                        Text(
                            text = "${stack?.timestamp?.getFormattedDateShort()}",
                            style = Info
                        )
                    }
                }
            }
        }
    }
}



