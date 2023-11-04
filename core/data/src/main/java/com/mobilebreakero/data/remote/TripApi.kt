package com.mobilebreakero.data.remote

import com.mobilebreakero.data.dto.Details
import com.mobilebreakero.domain.util.DataUtils.API_KEY
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
    ): Details


    @GET("place/details/json")
    suspend fun getPlaceDetails(
        @Query("placeid") placeId: String,
        @Query("fields") fields: String,
        @Query("key") apiKey: String = API_KEY
    ): retrofit2.Response<Details>

}