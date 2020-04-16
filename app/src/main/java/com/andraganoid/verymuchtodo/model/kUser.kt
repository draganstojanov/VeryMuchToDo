package com.andraganoid.verymuchtodo.model

import android.net.Uri

data class kUser(
        val uid: String,
        val name: String,
        val email: String,
        val photoUrl: Uri?)