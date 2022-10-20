package com.example.domain

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("title")
    var title: String?,
    @field:SerializedName("text")
    var text: String?,
    @field:SerializedName("photoPath")
    var urlPhoto: String?,
    @field:SerializedName("office")
    val office: Office?,
    @field:SerializedName("tag")
    val category: Category,
    @field:SerializedName("dataOfCreation")
    var dataOfCreation: String,
)

data class Category(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("tag")
    val tag: String
)

data class Office(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("city")
    val city: Int,
    @field:SerializedName("adress")
    val address: String
)
