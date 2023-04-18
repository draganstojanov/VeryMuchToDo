package com.andraganoid.verymuchtodo.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andraganoid.verymuchtodo.R
import com.andraganoid.verymuchtodo.ui.theme.ColorPrimaryVariantLite
import com.andraganoid.verymuchtodo.ui.theme.ColorText100
import com.andraganoid.verymuchtodo.ui.theme.ColorText25
import com.andraganoid.verymuchtodo.ui.theme.Desc
import com.andraganoid.verymuchtodo.ui.theme.Info
import com.andraganoid.verymuchtodo.ui.theme.ItemButtonStyle
import com.andraganoid.verymuchtodo.ui.theme.Title
import com.andraganoid.verymuchtodo.util.getFormattedShortDateAndTimeLocale


@Composable
fun ItemBottomRow(
    isCompleted: Boolean,
    onDeleteClick: () -> Unit,
    onEditCLick: () -> Unit,
    name: String,
    timestamp: Long?,
    ) {

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
                onCLick = onDeleteClick
            )

            ItemButton(
                content = stringResource(id = R.string.edit).uppercase(),
                isEnabled = true,
                onCLick = onEditCLick
            )

            ItemNameAndDate(name, timestamp)

        }
    }

}

@Composable
fun ItemTitle(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ColorPrimaryVariantLite)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        text = title, style = Title
    )
}

@Composable
fun ItemDescription(description: String?) {
    if (!description.isNullOrEmpty()) {
        Text(
            text = description,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            style = Desc
        )
    }
}

@Composable
fun ItemButton(
    content: String,
    isEnabled: Boolean,
    onCLick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .wrapContentWidth()
            .height(24.dp),
        elevation = null,
        enabled = isEnabled,
        onClick = onCLick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = ColorText100,
            disabledContentColor = ColorText25
        ),
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, color = if (isEnabled) ColorText100 else ColorText25),
    ) {
        Text(
            text = content,
            style = ItemButtonStyle
        )
    }
}

@Composable
fun ItemNameAndDate(
    name: String,
    timestamp: Long?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = name,
            style = Info
        )
        Text(
            text = "${timestamp?.getFormattedShortDateAndTimeLocale()}",
            style = Info
        )
    }
}