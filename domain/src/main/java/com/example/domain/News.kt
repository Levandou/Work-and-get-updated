package com.example.domain

data class News(
    val id: Int,
    var title: String,
    var text: String,
    val urlPhoto: String,
    val officeId: Int,
    var dataOfCreation: String
)