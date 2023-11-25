package com.mobilebreakero.data.repoimpl

import android.util.Log
import com.mobilebreakero.data.TextSearchRequest
import com.mobilebreakero.data.mapper.PlacesMapper
import com.mobilebreakero.data.remote.TripApi
import com.mobilebreakero.domain.model.PlacesItem
import com.mobilebreakero.domain.repo.SearchRepository
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchResultRepoImpl(
    private val apiServices: TripApi,
    private val placesMapper: PlacesMapper
) : SearchRepository {

    override suspend fun searchText(
        query: String,
        type: String,
        language: String,
    ): Flow<Response<List<PlacesItem>>> =
        flow {
            try {
                Log.e("SearchResultRepoImpl", "searchText: Before network call")

                val response = apiServices.searchText(TextSearchRequest(query, type, language))
                Log.e("SearchResultRepoImpl", "searchText: After network call")

                if (response.isSuccessful) {
                    val places = response.body()!!.places
                    if (places.isNotEmpty()) {
                        emit(Response.Success(places))
                    } else {
                        emit(Response.Success(emptyList())) // You can define an EmptyResult response type
                    }
                } else {
                    val errorMessage =
                        "Unsuccessful network response. HTTP Status Code: ${response.code()}, Error Body: ${
                            response.errorBody()?.string()
                        }"
                    Log.e("SearchResultRepoImpl", errorMessage)
                    Log.e(
                        "SearchResultRepoImpl",
                        TextSearchRequest(query, type, language).toString()
                    )
                    emit(Response.Failure(Exception(errorMessage)))
                }

            } catch (e: Exception) {
                val errorMessage = "searchText: Error - ${e.message}"
                Log.e("SearchResultRepoImpl", errorMessage)
                emit(Response.Failure(Exception(errorMessage)))
            }
        }.flowOn(Dispatchers.IO)
}
