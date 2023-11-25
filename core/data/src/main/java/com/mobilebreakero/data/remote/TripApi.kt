package com.mobilebreakero.data.remote

import com.mobilebreakero.data.TextSearchRequest
import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.model.PhotosResponse
import com.mobilebreakero.domain.model.PlacesItem
import com.mobilebreakero.domain.model.PlacesResponse
import com.mobilebreakero.domain.model.TripPlacesResponse
import com.mobilebreakero.domain.util.DataUtils.TRIP_API_KEY
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TripApi {

    @POST("places/searchText")
    suspend fun searchText(@Body request: TextSearchRequest): Response<PlacesResponse>


    @GET("location/search")
    suspend fun searchLocation(
        @Query("key") key: String? = TRIP_API_KEY,
        @Query("language") language: String,
        @Query("searchQuery") query: String,
        @Query("category") filter: String
    ): Response<TripPlacesResponse>


    @GET("location/{locationId}/details")
    suspend fun getDetails(
        @Path("locationId") locationId: String,
        @Query("key") key: String? = TRIP_API_KEY,
    ): Response<DetailsResponse>


    @GET("location/{locationId}/photos")
    suspend fun getPhotos(
        @Path("locationId") locationId: String,
        @Query("key") key: String? = TRIP_API_KEY,
    ): Response<PhotosResponse>

}