package com.mobilebreakero.domain.repo

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.flow.Flow

typealias addTripResponse = Response<Boolean>
typealias updateTripResponse = Response<Boolean>

interface TripsRepo {

    suspend fun getTrips(id: String): Flow<Response.Success<MutableList<Trip>>>

    suspend fun addTrip(
        trip: Trip, onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addTripResponse

    suspend fun addCheckList(
        checkList : List<String>,
        id : String
    ):updateTripResponse

    suspend fun addPlaces (
        places : List<String>,
        id : String
    ):updateTripResponse

}