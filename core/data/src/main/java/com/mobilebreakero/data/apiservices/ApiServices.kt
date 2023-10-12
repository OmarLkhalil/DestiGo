package com.mobilebreakero.data.apiservices

import com.mobilebreakero.domain.model.SearchResult
import com.mobilebreakero.domain.util.DataUtils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("search?")
    suspend fun getSearchResult(
        @Query("k") apiKey: String = API_KEY,
        @Query("q") searchText: String,
        @Query("t") searchType: String,
        @Query("l") searchLanguage: String
    ): Response<SearchResult?>
}