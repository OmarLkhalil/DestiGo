package com.mobilebreakero.domain.usecase.auth

import android.content.Context
import com.mobilebreakero.domain.repo.AuthRepository

class CheckUserSignedInUseCase(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, context: Context) =
        repo.checkUserSignedIn(email, password, context)
}