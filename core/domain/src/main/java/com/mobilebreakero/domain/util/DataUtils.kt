package com.mobilebreakero.domain.util

import com.mobilebreakero.domain.BuildConfig
import com.mobilebreakero.domain.model.AppUser


object DataUtils {

    var user: AppUser? = null

    const val API_KEY = BuildConfig.API_KEY

    const val TRIP_API_KEY = "48F0F0B67AF246DFAFE77A9A03610475"
    const val BASE_URL = "https://api.content.tripadvisor.com/api/v1/"

}