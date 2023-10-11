package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.repo.AuthRepository

class SignInWithEmailAndPassword(
    private val repo : AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repo.signInWithEmailAndPassword(email, password)

}