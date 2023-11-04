package com.mobilebreakero.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mobilebreakero.domain.model.PlaceItem
import com.mobilebreakero.domain.pagingsource.PlacesPagingSource
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
    ): Flow<Response<Pager<Int, PlaceItem>>> = flow {
        try {
            emit(Loading)
            val getPlaces = Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    PlacesPagingSource(
                        searchResultRepo,
                        location,
                        radius,
                        type,
                        language,
                        keyword
                    )
                }
            )
            emit(Success(getPlaces))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }
}