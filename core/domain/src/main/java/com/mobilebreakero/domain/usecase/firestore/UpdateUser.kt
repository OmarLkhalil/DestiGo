package com.mobilebreakero.domain.usecase.firestore

import com.mobilebreakero.domain.repo.FireStoreRepository

class UpdateUser(
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke(email: String) =
        repo.updateUser(id = email)
}