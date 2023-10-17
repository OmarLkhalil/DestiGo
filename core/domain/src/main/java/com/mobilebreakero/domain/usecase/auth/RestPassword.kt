package com.mobilebreakero.domain.usecase.auth

import com.mobilebreakero.domain.repo.AuthRepository

class RestPassword (
    private val repo: AuthRepository
){
    suspend operator fun invoke(email: String, confirmationCode: Int) = repo.sendResetPassword(
        email,
        confirmationCode
    )
}