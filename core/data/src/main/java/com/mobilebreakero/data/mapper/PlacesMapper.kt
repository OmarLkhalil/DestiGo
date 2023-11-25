package com.mobilebreakero.data.mapper

import com.mobilebreakero.data.dto.PlacesItem
import com.mobilebreakero.data.dto.PlacesResponse
import com.mobilebreakero.domain.model.PlaceItem
import com.mobilebreakero.domain.model.PlacesModel
import javax.inject.Inject

class PlacesMapper @Inject constructor() {

    fun fromRemotePlacesToPlacesModel(obj: PlacesResponse): PlacesModel {
        return PlacesModel(
            places = mapToPlace(obj.places),
            status = obj.status,
            page = obj.page,
            totalPage = obj.totalPage,
            totalResults = obj.places.size
        )
    }

    private fun mapToPlace(obj: List<PlacesItem>): List<PlaceItem> {

        return obj.map {
            PlaceItem(
                placeId = it.id,
                icon = it.iconMaskBaseUri,
                name = it.name,
                vicinity = it.shortFormattedAddress,
                photos = it.photos
            )
        }
    }
}