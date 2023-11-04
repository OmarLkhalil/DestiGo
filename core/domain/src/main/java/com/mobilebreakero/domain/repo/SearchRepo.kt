package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.PlacesModel

interface SearchResultRepo {
    suspend fun getResult(
        location: String,
        radius: Int,
        type: String,
        language: String,
        keyword: String
    ): PlacesModel?

}