package com.mobilebreakero.data.repoimpl

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.addUserResponse
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

    override suspend fun getUserById(id: String): userResponse {
        return try {
            val db = FirebaseFirestore.getInstance()
            val userDocument = db.collection(AppUser.COLLECTION_NAME).document(id).get().await()

            if (userDocument.exists()) {
                val appUser = userDocument.toObject(AppUser::class.java)
                appUser?.let { Response.Success(it) } ?: Response.Failure(Exception("User document is null"))
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

}