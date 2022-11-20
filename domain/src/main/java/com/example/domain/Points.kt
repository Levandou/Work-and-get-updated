package com.example.domain

data class Points(
    val id: Int? = null,
    val lat: String? = null,
    val lng: String? = null,
    val cityInstance: CityInstance? = null,
    val title: String? = null,
    val text: String? = null,
    val photoPath: String? = null,
    val likes: Int? = null,
    val disLikes: Int? = null,
    val tinkoffCashBack: Boolean? = null,
    val comments: String? = null,
    val dateOfCreation: String? = null,
    val mapPointType: MapPointType? = null
)

data class MapPointType(
    val id: Int? = null,
    val name: String? = null
)

data class CityInstance(
    val id: Int? = null,
    val mapPointType: String? = null
)