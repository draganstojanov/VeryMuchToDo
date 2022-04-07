package com.andraganoid.verymuchtodo.util

import android.content.res.Resources
import com.andraganoid.verymuchtodo.R


const val COL_LIST = "TODO_LIST"
const val KEY_TITLE = "title"
const val KEY_ID = "id"
const val KEY_DESCRIPTION = "description"
const val KEY_COMPLETED = "completed"
const val KEY_USER_NAME = "userName"
const val KEY_ITEM_LIST = "itemList"
const val KEY_TIMESTAMP = "timestamp"

const val MESSAGE_DURATION = 3000L

const val ARGS_DIALOG_DATA = "dialogData"

val ERROR_PLACEHOLDER: String = Resources.getSystem().getString(R.string.error)
val CANCELLED: String = Resources.getSystem().getString(R.string.cancelled)
val DOCUMENT_ERROR: String = Resources.getSystem().getString(R.string.document_error)