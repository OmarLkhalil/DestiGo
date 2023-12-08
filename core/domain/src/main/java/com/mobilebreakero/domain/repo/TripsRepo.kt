package com.mobilebreakero.domain.repo

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.model.TripPlace
import com.mobilebreakero.domain.util.Response

typealias addTripResponse = Response<Boolean>
typealias updateTripResponse = Response<Boolean>
typealias updatePlacesResponse = Response<Boolean>
typealias getTripsResponse = Response<List<Trip>>
typealias getTripDetailsResponse = Response<Trip>


interface TripsRepo {

    suspend fun getTrips(id: String): getTripsResponse

    suspend fun addTrip(
        trip: Trip, onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addTripResponse

    suspend fun addCheckList(
        checkList: List<String>,
        id: String
    ): updateTripResponse

    suspend fun addPlaces(
        placeName: String, placeId: String, placePhoto: String, id: String
    ): updatePlacesResponse

    fun updatePhoto(photo: String, id: String): updatePlacesResponse

    fun deleteTrip(id: String): updatePlacesResponse

    suspend fun getTripDetails(id: String): getTripDetailsResponse

    suspend fun getTripsByCategory(categories: String): getTripsResponse

}