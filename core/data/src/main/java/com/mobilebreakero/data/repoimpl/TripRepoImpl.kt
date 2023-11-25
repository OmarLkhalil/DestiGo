package com.mobilebreakero.data.repoimpl

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.addTripResponse
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.getCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepoImpl @Inject constructor() : TripsRepo {

    override suspend fun getTrips(id: String): Flow<Response.Success<MutableList<Trip>>> = flow {
        val tripCollection = getCollection(Trip.COLLECTION_NAME)
        val trips = tripCollection.get().await().toObjects(Trip::class.java)
        emit(Response.Success(trips))
    }

    override suspend fun addTrip(
        trip: Trip,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addTripResponse = try {
        val tripCollection = getCollection(Trip.COLLECTION_NAME)
        val tripDoc = tripCollection.document(trip.id!!)
        tripDoc.set(trip)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

}