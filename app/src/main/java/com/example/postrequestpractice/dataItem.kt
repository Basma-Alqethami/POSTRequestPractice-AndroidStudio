package com.example.postrequestpractice

import java.io.Serializable

data class dataItem(
    val location: String,
    val name: String,
    val pk: Int
): Serializable