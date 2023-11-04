package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.flow.Flow

typealias users = List<AppUser>
typealias UserResponse = Response<AppUser>
typealias addUserResponse = Response<Boolean>
typealias updateUserResponse = Response<Boolean>

interface FireStoreRepository {
    suspend fun getUsers(): Flow<Response<MutableList<AppUser>>>
    suspend fun getUserById(id: String): UserResponse
    suspend fun addUser(email: String, username: String): addUserResponse
    suspend fun updateUser(id: String): updateUserResponse
}