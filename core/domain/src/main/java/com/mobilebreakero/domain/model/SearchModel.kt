package com.mobilebreakero.domain.model

data class SearchModel(
    val results: List<Place>,
    val status: String,
    val errorMessage: String?,
)

data class Place(
    val name: String,
    val vicinity: String,
    )

data class Photo(
    val photo: String,
)