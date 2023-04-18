package com.andraganoid.verymuchtodo.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.composables.ItemBottomRow
import com.andraganoid.verymuchtodo.composables.ItemDescription
import com.andraganoid.verymuchtodo.composables.ItemTitle
import com.andraganoid.verymuchtodo.model.TodoItem
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
import com.andraganoid.verymuchtodo.ui.theme.ColorTaskDone

@Composable
fun TodoListItem(
    item: TodoItem?,
    onEditorCLick: () -> Unit,
    onDeleteCLick: () -> Unit,
) {

    val isCompleted = item?.completed == true

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
            ItemTitle(title = item?.content.toString())
            ItemDescription(description = item?.description.toString())
            ItemBottomRow(
                isCompleted = isCompleted,
                onDeleteClick = onDeleteCLick,
                onEditCLick = onEditorCLick,
                name = item?.userName.toString(),
                timestamp = item?.timestamp
            )

        }
    }
}