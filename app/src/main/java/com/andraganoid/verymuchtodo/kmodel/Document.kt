package com.andraganoid.verymuchtodo.kmodel

import com.andraganoid.verymuchtodo.util.COL_USER
import com.andraganoid.verymuchtodo.util.COL_МESSAGE

class Document(value: Any) {

    var collection: String = ""
    var name: String = ""
    var values: Map<String, Any?> = mapOf()

    init {
        when (value) {
            is User -> user(value)
            is Message -> message(value)
        }
    }

    private fun user(user: User) {
        collection = COL_USER
        name = user.uid
        values = mapOf(
                "uid" to user.uid,
                "name" to user.name,
                "email" to user.email,
                "imageUrl" to user.imageUrl,
                "accessLevel" to user.accessLevel
        )
    }

    private fun message(message: Message) {
        collection = COL_МESSAGE
        name = message.id
        values = mapOf(
                "text" to message.text,
                "timestamp" to message.timestamp,
                "user" to message.user,
                "id" to message.id
        )
    }

}
