package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.PlacesItem
import com.mobilebreakero.domain.model.PlacesModel
import com.mobilebreakero.domain.model.PlacesResponse
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchText(query: String, type: String, language: String): Flow<Response<List<PlacesItem>>>
}