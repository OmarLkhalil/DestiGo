package com.mobilebreakero.domain.util

import com.mobilebreakero.domain.model.DataItem
import com.mobilebreakero.domain.model.PlacesResponse
import com.mobilebreakero.domain.model.TripPlacesResponse


data class SearchState(
    val isLoading: Boolean = false,
    val items: TripPlacesResponse? = null,
    val error: String = ""
)
