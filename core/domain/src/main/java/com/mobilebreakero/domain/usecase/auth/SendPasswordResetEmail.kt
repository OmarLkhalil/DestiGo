package com.mobilebreakero.domain.usecase.auth

import com.mobilebreakero.domain.repo.AuthRepository

class SendPasswordResetEmail(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String) = repo.sendPasswordResetEmail(email)
}