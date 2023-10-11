package com.mobilebreakero.domain.usecase

import com.mobilebreakero.domain.repo.AuthRepository

class CurrentUser (
    private val repo : AuthRepository,
){
    operator fun invoke() = repo.currentUser
}