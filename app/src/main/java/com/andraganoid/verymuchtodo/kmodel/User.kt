package com.andraganoid.verymuchtodo.kmodel

data class User(
        val uid: String? = "",
        var name: String? = "",
        var email: String? = "",
        var photoUrlString: String? = null,
        val accessLevel: Int = 0)