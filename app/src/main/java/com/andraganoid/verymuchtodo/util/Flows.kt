package com.andraganoid.verymuchtodo.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

val keyboardState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
fun getKeyboardState(): StateFlow<Boolean> = keyboardState

val calculatorState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
fun getCalculatorState(): StateFlow<Boolean> = calculatorState