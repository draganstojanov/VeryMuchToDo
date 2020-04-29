package com.andraganoid.verymuchtodo.kmodel

import android.net.Uri

data class User(
        val uid: String?="",
        var name: String?="",
        var email: String?="",
        val photoUrl: Uri?=null) {

}