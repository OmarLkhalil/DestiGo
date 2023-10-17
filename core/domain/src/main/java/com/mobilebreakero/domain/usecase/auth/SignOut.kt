package com.mobilebreakero.domain.usecase.auth

import com.mobilebreakero.domain.repo.AuthRepository

class SignOut(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.signOut()

}