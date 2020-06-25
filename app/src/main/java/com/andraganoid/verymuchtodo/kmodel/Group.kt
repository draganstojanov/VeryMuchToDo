package com.andraganoid.verymuchtodo.kmodel

import java.io.Serializable

//TODO
data class Group(
        val name: String = "",
        val description: String = "",
        val colorIndex: Int = 0
) : Serializable