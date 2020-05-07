package com.andraganoid.verymuchtodo.kmodel

import com.andraganoid.verymuchtodo.util.COL_USER

class Document(value: Any) {

    var collection: String = ""
    var name: String = ""
    var values: Map<String, Any?> = mapOf()

    init {
        when (value) {
            is User -> user(value)
        }
    }

    private fun user(user: User) {
        collection = COL_USER
        name = user.uid.toString()
        values = mapOf(
                "uid" to user.uid,
                "name" to user.name,
                "email" to user.email,
                "imageUrl" to user.imageUrl,
                "accessLevel" to user.accessLevel
        )
    }

}