package com.example.domain

import com.google.gson.annotations.SerializedName

//@Entity(tableName = "news")
data class  News(
  //  @PrimaryKey
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("title")
    var title: String?,
    @field:SerializedName("text")
    var text: String?,
    @field:SerializedName("photoPath")
    var urlPhoto: String?,
    @field:SerializedName("officeId")
    var officeId: Int?,
    @field:SerializedName("dataOfCreation")
    var dataOfCreation: String
)