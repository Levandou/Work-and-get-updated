package com.example.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    var id: Int,
    var title: String?,
    var text: String?,
    var urlPhoto: String?,
    var officeId: Int?,
    val cityOfOffice: Int?,
    val addressOfOffice: String? = "",
    var dataOfCreation: String? = "",
    var tagId: Int?,
    var tagTitle: String? = "",

    var isPointOpened: Boolean = false
)