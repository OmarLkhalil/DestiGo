package com.mobilebreakero.data.repoimpl

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.addUserResponse
import com.mobilebreakero.domain.repo.updateUserResponse
import com.mobilebreakero.domain.repo.userResponse
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.getCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreRepoImpl @Inject constructor(
    private val usersRef: CollectionReference
) : FireStoreRepository {

    override suspend fun getUsers(): Flow<userResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: String): userResponse {
        return try {
            Log.e("A7A", "LoL1")
            val db = FirebaseFirestore.getInstance()
            val userDocument = db.collection(AppUser.COLLECTION_NAME).document(id).get().await()

            if (userDocument.exists()) {
                // User document exists, parse it into an AppUser
                Log.e("A7A", "LoL2")
                val appUser = userDocument.toObject(AppUser::class.java)
                appUser?.let { Response.Success(it) } ?: Response.Failure(Exception("User document is null"))
            } else {
                // User document does not exist
                Log.e("A7A", "LoL3")
                Response.Failure(Exception("User document with ID $id not found"))
            }
        } catch (e: Exception) {
            Log.e("A7A", "LoL4")
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
            Log.e("Success1", "DocumentSnapshot successfully written!")
            Response.Success(true)
        } catch (e: Exception) {
            Log.e("Failed11", "Error writing document", e)
            Response.Failure(e)
        }
    }

    override suspend fun updateUser(id: String): updateUserResponse {
        TODO("Not yet implemented")
    }

}