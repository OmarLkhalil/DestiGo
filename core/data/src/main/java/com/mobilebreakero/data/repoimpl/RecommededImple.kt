package com.mobilebreakero.data.repoimpl

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import com.mobilebreakero.domain.model.RecommendedPlaceItem
import com.mobilebreakero.domain.model.RecommendedPlaces
import com.mobilebreakero.domain.model.RecommendedTripsModel
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.repo.RecommendedTrips
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.await
import com.mobilebreakero.domain.util.getCollection
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommededImple @Inject constructor(
    private val context: Context
) : RecommendedTrips {

    override suspend fun getRecommendation(userInterests: List<String>): List<TripsItem?> {
        return try {
            val inputStream = context.assets.open("PublicTrips.json")
            val reader = InputStreamReader(inputStream)
            val tripsList = Gson().fromJson(reader, RecommendedTripsModel::class.java)

            Log.d("RecommendedImpl", "Trips List: $tripsList")

            val filteredTrips =
                if (userInterests.isNotEmpty())
                    tripsList.trips?.filter { trip ->
                        userInterests.any { it.lowercase() == trip?.category?.lowercase() }
                    } ?: tripsList.trips!!
                else {
                    tripsList.trips!!
                }

            Log.d("RecommendedImpl", "User Interests: $userInterests")
            Log.d("RecommendedImpl", "Filtered Trips: $filteredTrips")

            filteredTrips
        } catch (e: Exception) {
            Log.e("RecommendedImpl", "Error getting recommendations: $e")
            emptyList()
        }
    }

    override suspend fun getRecommendationPlaces(userInterests: List<String>): List<RecommendedPlaceItem?> {
        return try {
            val inputStream = context.assets.open("Places.json")
            val reader = InputStreamReader(inputStream)
            val placesList = Gson().fromJson(reader, RecommendedPlaces::class.java)

            Log.d("RecommendedImpl", "Places List: $placesList")

            val filteredPlaces =
                if (userInterests.isNotEmpty())
                    placesList.places?.filter { place ->
                        userInterests.any { it.lowercase() == place?.category?.lowercase() }
                    } ?: placesList.places!!
                else {
                    placesList.places!!
                }

            Log.d("RecommendedImpl", "User Interests: $userInterests")
            Log.d("RecommendedImpl", "Filtered Places: $filteredPlaces")

            filteredPlaces
        } catch (e: Exception) {
            Log.e("RecommendedImpl", "Error getting recommendations: $e")
            emptyList()
        }
    }

    override suspend fun getPublicTrips(tripId: String): Response<TripsItem?> {
        return try {
            val tripDoc =
                FirebaseFirestore.getInstance().collection(TripsItem.COLLECTION_NAME)
                    .document(tripId).get().await()
            val trip = tripDoc.toObject(TripsItem::class.java)
            Response.Success(trip)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updatePublicTripDate(
        tripId: String,
        startDate: String?,
        endDate: String?
    ): Response<Boolean> {
        return try {
            val tripCollection = getCollection(TripsItem.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            if (startDate != null) {
                tripDoc.update("fullJourney.startDate", startDate)
            }

            if (endDate != null) {
                tripDoc.update("fullJourney.endDate", endDate)
            }

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updatePublicTripDays(tripId: String, days: String): Response<Boolean> {
        return try {
            val tripCollection = getCollection(TripsItem.COLLECTION_NAME)
            val tripDoc = tripCollection.document(tripId)
            tripDoc.update("fullJourney.days", days)

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}
