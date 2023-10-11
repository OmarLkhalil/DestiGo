package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.repo.AuthRepository

class SignOut(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() = repo.signOut()

}