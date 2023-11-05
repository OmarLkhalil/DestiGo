package com.mobilebreakero.domain.model


data class AppUser(
    var id: String? = null,
    var name:String? = "Omar",
    var email: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "users"
    }
}