package com.mobilebreakero.data.repoimpl

import com.mobilebreakero.data.remote.TripApi
import com.mobilebreakero.domain.model.SearchModel
import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Failure

class SearchResultRepoImpl(
    private val apiServices: TripApi
) : SearchResultRepo {
    override suspend fun getResult(
        location: String,
        radius: Int,
        type: String,
        language: String,
        keyword: String,
    ): Response<SearchModel> {
        return try {
            val response = apiServices.getNearbyPlaces(location, radius, type, language, keyword)
            if (response.isSuccessful) {
                Success(response.body()!!)
            } else {
                Failure(Exception("API request failed with code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Failure(e)
        }
    }
}