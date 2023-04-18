package com.andraganoid.verymuchtodo.ui.theme

import androidx.compose.material.Typography
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
    fontSize = 20.sp,
    color = ColorText100
)

val Title = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W600,
    fontSize = 18.sp,
    color = ColorText100
)

val Desc = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W300,
    fontSize = 16.sp,
    color = ColorText75
)

val Info = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W400,
    fontSize = 12.sp,
    color = ColorText75
)

val ItemButtonStyle = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W300,
    fontSize = 10.sp,
)

val SmallButtonStyle = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
)

val InputStyle = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    color = ColorText100
)


val Typography = Typography()