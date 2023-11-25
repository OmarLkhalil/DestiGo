package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.model.PlacesItem
import com.mobilebreakero.domain.repo.SearchRepository
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchResultUseCase @Inject constructor(private val searchResultRepo: SearchRepository) {

    suspend operator fun invoke(
        location: String,
        type: String,
        language: String,
    ): Flow<Response<List<PlacesItem>>> = searchResultRepo.searchText(location, type, language)
}