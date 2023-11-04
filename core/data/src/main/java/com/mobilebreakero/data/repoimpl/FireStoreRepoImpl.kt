package com.mobilebreakero.data.repoimpl

import com.google.firebase.firestore.CollectionReference
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.UserResponse
import com.mobilebreakero.domain.repo.addUserResponse
import com.mobilebreakero.domain.repo.updateUserResponse
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireStoreRepoImpl @Inject constructor(
    private val usersRef: CollectionReference
) : FireStoreRepository {

    override suspend fun getUsers(): Flow<Response<MutableList<AppUser>>> = callbackFlow {
        val snapShotListener = usersRef.orderBy("username").addSnapshotListener { snapshot, error ->
            val usersResponse = if (snapshot != null) {
                Response.Success(snapshot.toObjects(AppUser::class.java))
            } else {
                Response.Failure(error!!)
            }
            trySend(usersResponse)
        }
        awaitClose {
            snapShotListener.remove()
        }
    }


    override suspend fun getUserById(id: String): UserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(email: String, username: String): addUserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(id: String): updateUserResponse {
        TODO("Not yet implemented")
    }

}