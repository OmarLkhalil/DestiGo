package com.mobilebreakero.domain.repo

import com.google.firebase.auth.FirebaseUser
import com.mobilebreakero.domain.util.Resource

interface AuthRepository {

    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
    fun logout()
}