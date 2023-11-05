package com.mobilebreakero.domain.model


data class PlacesModel(
    val places: List<PlaceItem> = emptyList(),
    val status: String,
    val page: Long = 0L,
    val totalPage: Int = 0,
    val totalResults: Int = 0
)

data class PlaceItem(
    val icon: String? = null,
    val name: String? = null,
    val vicinity: String? = null,
    val photos: List<PhotoItem?>? = null,
    val placeId: String? = null
)