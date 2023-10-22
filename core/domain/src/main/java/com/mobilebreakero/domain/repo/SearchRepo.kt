package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.SearchModel
import com.mobilebreakero.domain.util.Response

interface SearchResultRepo {
    suspend fun getResult(
        location: String,
        radius: Int,
        type: String,
        language: String,
        keyword: String,
    ): Response<SearchModel>
}