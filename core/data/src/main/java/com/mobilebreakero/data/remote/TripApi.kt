package com.mobilebreakero.data.remote

import com.mobilebreakero.domain.model.SearchModel
import com.mobilebreakero.domain.util.DataUtils.API_KEY
import io.grpc.android.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface TripApi {

    @GET("place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("keyword") keyword: String,
        @Query("language") language: String,
        @Query("key") apiKey: String = API_KEY,
    ): retrofit2.Response<SearchModel>

}