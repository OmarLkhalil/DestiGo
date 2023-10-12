package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.model.ResultsItem
import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.util.Resource

class SearchResultUseCase(private val searchResultRepo: SearchResultRepo) {

    suspend fun invoke(text: String?, type: String?):
            Resource<List<ResultsItem?>> = searchResultRepo.getResult(text = text, type = type)
}