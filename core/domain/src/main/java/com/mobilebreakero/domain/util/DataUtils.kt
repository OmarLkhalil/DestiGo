package com.mobilebreakero.domain.util

import com.google.firebase.firestore.auth.User

object DataUtils {

    var user: com.mobilebreakero.domain.model.AppUser? = null

    var firebaseUser: User? = null

    const val API_KEY = "48F0F0B67AF246DFAFE77A9A03610475"

    const val BASE_URL = "https://api.content.tripadvisor.com/api/v1/location/"
}