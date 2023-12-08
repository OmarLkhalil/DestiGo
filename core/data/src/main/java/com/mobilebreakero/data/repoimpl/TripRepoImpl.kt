package com.mobilebreakero.data.repoimpl

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.mobilebreakero.domain.model.Comment
import com.mobilebreakero.domain.model.PlaceItem
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.model.TripPlace
import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.repo.addTripResponse
import com.mobilebreakero.domain.repo.getTripsResponse
import com.mobilebreakero.domain.repo.updatePlacesResponse
import com.mobilebreakero.domain.repo.updateTripResponse
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.getCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepoImpl @Inject constructor() : TripsRepo {

    override suspend fun getTrips(id: String): getTripsResponse {

        return try {
            val db = FirebaseFirestore.getInstance()
            val tripDoc = db.collection(Trip.COLLECTION_NAME)
                .whereEqualTo("userId", id)
                .get()
                .await()

            if (!tripDoc.isEmpty) {
                val trips = tripDoc.toObjects(Trip::class.java) as List<Trip>
                trips.let { Response.Success(it) }
            } else {
                Response.Failure(Exception("Not found any trips"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
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

    override suspend fun addCheckList(checkList: List<String>, id: String): updateTripResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(id)
            tripDoc.update("checkList", checkList)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addPlaces(
        placeName: String,
        placeId: String,
        placePhoto: String,
        id: String
    ): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val newPlace = TripPlace(
                name = placeName,
                tripId = id,
                location = placeId,
                image = placePhoto
            )
            val tripDoc = tripCollection.document(id)
            tripDoc.update("places", FieldValue.arrayUnion(newPlace))
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun updatePhoto(photo: String, id: String): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(id)
            tripDoc.update("image", photo)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun deleteTrip(id: String): updatePlacesResponse {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(id)
            tripDoc.delete()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getTripDetails(id: String): Response<Trip> {
        return try {
            val tripCollection = getCollection(Trip.COLLECTION_NAME)
            val tripDoc = tripCollection.document(id)
            val trip = tripDoc.get().await().toObject(Trip::class.java)
            Response.Success(trip!!)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getTripsByCategory(categories: String): getTripsResponse {
        return try {
            val db = FirebaseFirestore.getInstance()

            val tripDoc = db.collection(Trip.COLLECTION_NAME)
                .whereArrayContains("category", categories)
                .get()
                .await()

            if (!tripDoc.isEmpty) {
                val trips = tripDoc.toObjects<Trip>()
                Response.Success(trips)
            } else {
                Response.Failure(Exception("No trips found for the specified category"))
            }
        } catch (e: Exception) {
            Response.Failure(Exception("Failed to fetch trips", e))
        }
    }


}