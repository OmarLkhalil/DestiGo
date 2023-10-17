package com.mobilebreakero.domain.usecase.firestore

import com.mobilebreakero.domain.repo.FireStoreRepository

class GetUsers (
    private val repo: FireStoreRepository
) {
    suspend operator fun invoke() =
        repo.getUsers()
}