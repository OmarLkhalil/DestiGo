package com.mobilebreakero.data.repoimpl

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.addUserResponse
import com.mobilebreakero.domain.repo.tripsResponseInterested
import com.mobilebreakero.domain.repo.updateUserResponse
import com.mobilebreakero.domain.repo.userResponse
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.getCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreRepoImpl @Inject constructor() : FireStoreRepository {

    override suspend fun getUsers(): Flow<Response.Success<MutableList<AppUser>>> = flow {
        val userCollection = getCollection(AppUser.COLLECTION_NAME)
        val users = userCollection.get().await().toObjects(AppUser::class.java)
        emit(Response.Success(users))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateUserStatues(id: String, status: String): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("status", status)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateUserLocation(id: String, location: String): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("location", location)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateUserPhotoUrl(id: String, photoUrl: String): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("photoUrl", photoUrl)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getUserById(id: String): userResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val userDocument = db.collection(AppUser.COLLECTION_NAME).document(id).get().await()

            if (userDocument.exists()) {
                val appUser = userDocument.toObject(AppUser::class.java)
                appUser?.let { Response.Success(it) }
                    ?: Response.Failure(Exception("User document is null"))
            } else {
                Response.Failure(Exception("User document with ID $id not found"))
            }
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addUser(
        user: AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(user.id!!)
            userDoc.set(user)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateUser(id: String, name: String): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("name", name)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun updateUserInterestedPlaces(
        id: String,
        interestedPlaces: List<String>
    ): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("interestedPlaces", interestedPlaces)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getUserTripsBasedOnInterestedPlaces(id: String): tripsResponseInterested {
        return try {
            val db = FirebaseFirestore.getInstance()

            val userDocument = db.collection(AppUser.COLLECTION_NAME).document(id).get().await()
            val user = userDocument.toObject(AppUser::class.java)
            val userInterests = user?.interestedPlaces
            Log.e("userInterests", userInterests.toString())

            val trips = mutableListOf<Trip>()

            if (userInterests != null) {
                for (interestedPlace in userInterests) {
                    val tripDoc = db.collection(Trip.COLLECTION_NAME)
                        .whereArrayContains("category", interestedPlace)
                        .get()
                        .await()

                    Log.e(
                        "tripDoc",
                        "Interested Place: $interestedPlace, Result: ${tripDoc.documents}"
                    )

                    if (!tripDoc.isEmpty) {
                        trips.addAll(tripDoc.toObjects())
                    }
                }

                if (trips.isNotEmpty()) {
                    Response.Success(trips)
                } else {
                    Response.Failure(Exception("No trips found for the specified categories"))
                }
            } else {
                Response.Failure(Exception("User's interested places list is null"))
            }
        } catch (e: Exception) {
            Response.Failure(Exception("Failed to fetch trips", e))
        }
    }


    override suspend fun updateUserSaved(id: String, saved: List<String>): updateUserResponse {
        return try {
            val userCollection = getCollection(AppUser.COLLECTION_NAME)
            val userDoc = userCollection.document(id)
            userDoc.update("saved", saved)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}
