package com.mobilebreakero.domain.usecase.auth

import com.mobilebreakero.domain.repo.AuthRepository

class SignUpWithEmailAndPassword(
    private val repo : AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ) = repo.signUpWithEmailAndPassword(name, email, password)

}