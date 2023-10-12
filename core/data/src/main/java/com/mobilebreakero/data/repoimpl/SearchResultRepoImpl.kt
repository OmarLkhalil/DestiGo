package com.mobilebreakero.data.repoimpl

import com.mobilebreakero.data.apiservices.ApiServices
import com.mobilebreakero.domain.model.ResultsItem
import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.util.Resource

class SearchResultRepoImpl(
    private val apiServices: ApiServices
) : SearchResultRepo {
//    override suspend fun getResult(text: String?, type: String?): Resource<List<ResultsItem?>> {
override suspend fun getResult(text: String?, type: String?): Resource<List<ResultsItem?>> {
        return try {
            val response = apiServices.getSearchResult(
                searchText = text!!,
                searchType = type!!,
                searchLanguage = "en"
            )

            if (response.isSuccessful) {
                val data = response.body()?.data
                if (data != null) {
                    Resource.Success(data)
                } else {
                    Resource.Error("No data available")
                }
            } else {
                Resource.Error("Network request failed")
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "An error occurred")
        }
    }
}