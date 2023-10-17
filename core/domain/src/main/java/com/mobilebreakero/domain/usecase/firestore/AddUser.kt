package com.mobilebreakero.domain.usecase.firestore

import com.mobilebreakero.domain.repo.FireStoreRepository

class AddUser(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(email: String, userName: String) =
        repo.addUser(email, userName)
}