package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.repo.AuthRepository

class SendPasswordResetEmail(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String) = repo.sendPasswordResetEmail(email)
}