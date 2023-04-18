package com.andraganoid.verymuchtodo.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


fun Long.getFormattedShortDateAndTimeLocale(): String {
    return if (this > 0) {
        val currentZonedTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        currentZonedTime.format(dateFormatter)
    } else ""
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

