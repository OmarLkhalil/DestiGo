package com.mobilebreakero.domain.usecase.auth

import com.mobilebreakero.domain.repo.AuthRepository

class CurrentUser (
    private val repo : AuthRepository,
){
    operator fun invoke() = repo.currentUser
}