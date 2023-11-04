package com.mobilebreakero.data.mapper

import com.mobilebreakero.data.dto.Details
import com.mobilebreakero.domain.model.PlaceItem
import com.mobilebreakero.domain.model.PlacesModel
import com.mobilebreakero.domain.model.ResultsItem
import com.mobilebreakero.domain.util.Response
import javax.inject.Inject

class PlacesMapper @Inject constructor() {

    fun fromRemotePlacesToPlacesModel(obj: Details): PlacesModel {
        return PlacesModel(
            places = mapToPlace(obj.results),
            status = obj.status,
            page = obj.page,
            totalPage = obj.totalPage,
            totalResults = obj.results.size
        )
    }

    private fun mapToPlace(obj: List<ResultsItem>): List<PlaceItem> {

        return obj.map {
            PlaceItem(
                placeId = it.placeId,
                icon = it.icon,
                name = it.name,
                vicinity = it.vicinity,
                photos = it.photos
            )
        }
    }
}