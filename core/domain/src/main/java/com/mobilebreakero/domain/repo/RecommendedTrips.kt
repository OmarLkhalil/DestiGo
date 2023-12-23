package com.mobilebreakero.domain.repo

import com.mobilebreakero.domain.model.RecommendedPlaceItem
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.util.Response

interface RecommendedTrips {
    suspend fun getRecommendation(userInterests: List<String>): List<TripsItem?>
    suspend fun getPublicTrips(tripId: String): Response<TripsItem?>
    suspend fun getRecommendationPlaces(userInterests: List<String>): List<RecommendedPlaceItem?>
}