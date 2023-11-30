package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class AddPlaces @Inject constructor(
    private val repo: TripsRepo
)
{
    suspend operator fun invoke (
        places: List<String>,
        id: String
    ) =
        repo.addPlaces(places, id)

}