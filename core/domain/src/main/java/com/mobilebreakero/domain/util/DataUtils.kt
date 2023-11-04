package com.mobilebreakero.domain.util

import com.google.firebase.firestore.auth.User
import com.mobilebreakero.domain.BuildConfig
import com.mobilebreakero.domain.model.AppUser


object DataUtils {

    var user: AppUser? = null

    var firebaseUser: User? = null

    const val API_KEY = BuildConfig.API_KEY

    const val BASE_URL = "https://maps.googleapis.com/maps/api/"


}