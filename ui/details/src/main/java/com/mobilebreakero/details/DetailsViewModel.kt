package com.mobilebreakero.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.repo.getTripDetailsResponse
import com.mobilebreakero.domain.repo.getTripsResponse
import com.mobilebreakero.domain.usecase.DetailsUseCase
import com.mobilebreakero.domain.usecase.PhotoUseCase
import com.mobilebreakero.domain.usecase.firestore.trips.TripsUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase,
    private val detailsUseCase: DetailsUseCase,
    private val tripsUseCase: TripsUseCase
) : ViewModel() {


    private val _photo =
        MutableStateFlow<Response<List<PhotoDataItem?>>>(Response.Success(listOf()))
    val photo: StateFlow<Response<List<PhotoDataItem?>>> = _photo

    fun getPhoto(locationId: String) {
        viewModelScope.launch {
            try {
                _photo.value = Response.Loading
                val result = photoUseCase.invoke(locationId)
                _photo.value = result
            } catch (e: Exception) {
                _photo.value = Response.Failure(e)
            }
        }
    }

    private val details =
        MutableStateFlow<Response<DetailsResponse>>(Response.Success(DetailsResponse()))

    val detailsResult: StateFlow<Response<DetailsResponse>> = details

    fun getDetails(id: String) {
        viewModelScope.launch {
            try {
                details.value = Response.Loading
                val result = detailsUseCase.invoke(id)
                details.value = result
            } catch (e: Exception) {
                details.value = Response.Failure(e)
            }
        }
    }

    private val tripDetails =
        MutableStateFlow<getTripDetailsResponse>(Response.Success(Trip()))

    val tripDetailsResult: StateFlow<getTripDetailsResponse> = tripDetails

    fun getTripDetailsResult(id: String) {
        viewModelScope.launch {
            try {
                tripDetails.value = Response.Loading
                val result = tripsUseCase.getTripDetails(id)
                tripDetails.value = result
            } catch (e: Exception) {
                tripDetails.value = Response.Failure(e)
            }
        }
    }

}