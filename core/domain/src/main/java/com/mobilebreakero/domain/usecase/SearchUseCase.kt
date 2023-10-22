package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.model.SearchModel
import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.Response.Loading
import com.mobilebreakero.domain.util.Response.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchResultUseCase @Inject constructor(private val searchResultRepo: SearchResultRepo) {

    operator fun invoke(
        location: String,
        radius: Int,
        type: String,
        language: String,
        keyword: String
    ): Flow<Response<SearchModel>> = flow {
        emit(Loading)
        when (val response = searchResultRepo.getResult(location, radius, type, language, keyword)) {
            is Success -> {
                emit(Success(response.data))
            }

            is Failure -> {
                emit(Failure(response.e))
            }
            else -> {

            }
        }
    }
}