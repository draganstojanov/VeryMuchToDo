package com.andraganoid.verymuchtodo.kmodel

import android.net.Uri

data class User(
        val uid: String?="",
        val name: String?="",
        val email: String?="",
        val photoUrl: Uri?=null) {

}