package com.mobilebreakero.domain.usecase.auth

import com.mobilebreakero.domain.repo.AuthRepository

class UpdatePassword (
    private val repo: AuthRepository
){
    suspend operator fun invoke(password: String) = repo.resetPassword(password)
}