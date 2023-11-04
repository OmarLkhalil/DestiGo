package com.mobilebreakero.data.repoimpl

import com.mobilebreakero.data.remote.TripApi
import com.mobilebreakero.data.mapper.PlacesMapper
import com.mobilebreakero.domain.model.PlacesModel
import com.mobilebreakero.domain.repo.SearchResultRepo

class SearchResultRepoImpl(
    private val apiServices: TripApi,
    private val placesMapper: PlacesMapper
) : SearchResultRepo {

    override suspend fun getResult(
        location: String,
        radius: Int,
        type: String,
        language: String,
        keyword: String
    ): PlacesModel {
        return placesMapper.fromRemotePlacesToPlacesModel(
            apiServices.getNearbyPlaces(
                location,
                radius,
                type,
                keyword,
                language
            )
        )
    }
}