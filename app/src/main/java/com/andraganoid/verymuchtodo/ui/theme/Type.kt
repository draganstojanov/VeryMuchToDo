package com.andraganoid.verymuchtodo.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.andraganoid.verymuchtodo.R


val font = FontFamily(Font(R.font.red_hat_display))

val TopTitle = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W400,
    fontSize = 20.sp
)

val Title = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W600,
    fontSize = 18.sp
)

val Desc = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W300,
    fontSize = 16.sp,
    color = Color.White.copy(alpha = .8f)
)

val Info = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W400,
    fontSize = 12.sp,
    color = Color.White.copy(alpha = .6f)
)

val Typography = Typography()