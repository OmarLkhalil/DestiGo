package com.mobilebreakero.domain.model


data class AppUser(
    var id: String? = null,
    var name: String? = "User",
    var email: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "users"
    }
}