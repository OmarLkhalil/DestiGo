package com.mobilebreakero.domain.usecase.firestore.trips

import com.mobilebreakero.domain.model.TripPlace
import com.mobilebreakero.domain.repo.TripsRepo
import javax.inject.Inject

class AddPlaces @Inject constructor(
    private val repo: TripsRepo
) {
    suspend operator fun invoke(
        placeName: String, placeId: String, placePhoto: String, id: String
    ) =
        repo.addPlaces(
            placeName = placeName,
            placeId = placeId,
            placePhoto = placePhoto,
            id = id
        )

}