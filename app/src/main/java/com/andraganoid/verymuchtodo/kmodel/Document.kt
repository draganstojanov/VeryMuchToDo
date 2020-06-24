package com.andraganoid.verymuchtodo.kmodel

import com.andraganoid.verymuchtodo.util.COL_CHAT
import com.andraganoid.verymuchtodo.util.COL_STACK
import com.andraganoid.verymuchtodo.util.COL_USER
import com.andraganoid.verymuchtodo.util.FIELD_MEMBERS

class Document(value: Any) {

    var collection: String = ""
    var name: String = ""
    var values: Map<String, Any?> = mapOf()

    init {
        when (value) {
            is User -> user(value)
            is Chat -> chat(value)
            is Stack -> stack(value)
        }
    }

    //TODO
    private fun stack(stack: Stack) {
        collection= COL_STACK
        values = mapOf(

              //  "name" to ,

        )
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

    private fun chat(chat: Chat) {
        collection = COL_CHAT
        name = chat.id
        values = mapOf(
                "id" to chat.id,
                "name" to chat.name,
                FIELD_MEMBERS to chat.members,
                "messages" to chat.messages,
                "lastRead" to chat.lastRead,
                "lastEdit" to chat.lastEdit
        )
    }



}
