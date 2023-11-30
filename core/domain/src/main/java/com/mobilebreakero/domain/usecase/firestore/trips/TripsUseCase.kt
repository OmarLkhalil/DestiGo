package com.mobilebreakero.domain.usecase.firestore.trips


data class TripsUseCase(
    val addTrip: AddTrip,
    val getTrips: GetTrips,
    val chickList: AddChickList,
    val places: AddPlaces
)