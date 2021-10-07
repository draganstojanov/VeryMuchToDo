package com.andraganoid.verymuchtodo.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

val _keyboardState: MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(false)
val keyboardState: StateFlow<Boolean> get() = _keyboardState