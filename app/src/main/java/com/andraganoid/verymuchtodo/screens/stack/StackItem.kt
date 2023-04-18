package com.andraganoid.verymuchtodo.screens.stack

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
import com.andraganoid.verymuchtodo.model.TodoStack
import com.andraganoid.verymuchtodo.model.isCompleted
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryLite
import com.andraganoid.verymuchtodo.ui.theme.ColorTaskDone
import com.andraganoid.verymuchtodo.util.noRippleClickable

@Composable
fun StackItem(
    item: TodoStack?,
    onItemSelected: (String?) -> Unit,
    onEditorCLick: () -> Unit,
    onDeleteCLick: () -> Unit,
) {

    val isCompleted = item?.isCompleted() == true

    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.cornerRadius)),
        modifier = Modifier
            .noRippleClickable { onItemSelected.invoke(item?.id) }
            .fillMaxWidth()
            .padding(top = 4.dp, start = 4.dp, end = 4.dp),
        backgroundColor = if (isCompleted) ColorTaskDone else ColorPrimaryLite
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            ItemTitle(title = item?.title.toString())
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
