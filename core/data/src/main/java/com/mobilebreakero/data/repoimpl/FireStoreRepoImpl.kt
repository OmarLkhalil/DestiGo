package com.mobilebreakero.data.repoimpl

import com.google.firebase.firestore.CollectionReference
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.addUserResponse
import com.mobilebreakero.domain.repo.updateUserResponse
import com.mobilebreakero.domain.repo.userResponse
import kotlinx.coroutines.flow.Flow
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
        TODO("Not yet implemented")
    }

    override suspend fun addUser(email: String, username: String): addUserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(id: String): updateUserResponse {
        TODO("Not yet implemented")
    }

}