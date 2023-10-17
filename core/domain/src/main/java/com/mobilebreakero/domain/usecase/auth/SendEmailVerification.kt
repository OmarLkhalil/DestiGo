package com.mobilebreakero.domain.usecase.auth

import com.mobilebreakero.domain.repo.AuthRepository

class SendEmailVerification(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.sendEmailVerification()
}