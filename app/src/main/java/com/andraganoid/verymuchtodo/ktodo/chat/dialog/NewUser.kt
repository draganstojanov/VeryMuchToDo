package com.andraganoid.verymuchtodo.ktodo.chat.dialog

import androidx.databinding.ObservableBoolean
import com.andraganoid.verymuchtodo.kmodel.User

data class NewUser(val user: User, var isSelected: ObservableBoolean)