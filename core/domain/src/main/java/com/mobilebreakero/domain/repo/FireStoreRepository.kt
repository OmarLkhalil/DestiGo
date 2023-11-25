package com.mobilebreakero.domain.repo

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.flow.Flow

typealias users = List<AppUser>
typealias userResponse = Response<AppUser>
typealias addUserResponse = Response<Boolean>
typealias updateUserResponse = Response<Boolean>

interface FireStoreRepository {

    suspend fun getUsers(): Flow<Response.Success<MutableList<AppUser>>>
    suspend fun getUserById(id: String): userResponse
    suspend fun addUser(
        user: AppUser,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addUserResponse

    suspend fun updateUser(id: String, name: String): updateUserResponse
}