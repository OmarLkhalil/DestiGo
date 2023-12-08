package com.mobilebreakero.trips

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.domain.model.DataItem
import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.repo.addTripResponse
import com.mobilebreakero.domain.repo.getTripsResponse
import com.mobilebreakero.domain.repo.updateTripResponse
import com.mobilebreakero.domain.usecase.PhotoUseCase
import com.mobilebreakero.domain.usecase.SearchPlacesUseCase
import com.mobilebreakero.domain.usecase.firestore.trips.TripsUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TripsViewModel @Inject constructor(
    private val tripsUseCase: TripsUseCase,
    private val useCase: SearchPlacesUseCase,
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    val currentUser = Firebase.auth.currentUser?.uid

    init {
        getTrips(userId = currentUser ?: "")
    }

    private val _tripsFlow = MutableStateFlow<getTripsResponse>(Response.Loading)
    val tripsFlow: StateFlow<getTripsResponse> get() = _tripsFlow

    var tripsResult by mutableStateOf(listOf<Trip>())

    fun getTrips(userId: String) {
        viewModelScope.launch {
            try {
                val result = tripsUseCase.getTrips(userId)
                if (result is Response.Success) {
                    val trips = result.data
                    tripsResult = trips
                    _tripsFlow.value = Response.Success(trips)
                } else {
                    _tripsFlow.value = result

                }
            } catch (e: Exception) {
                _tripsFlow.value = Response.Failure(e)
            }
        }
    }

    private var addTripResponse by mutableStateOf<addTripResponse>(Response.Success(false))
        private set

    fun addTripToFireStore(trip: Trip) = viewModelScope.launch {
        addTripResponse = Response.Loading
        addTripResponse = tripsUseCase.addTrip(trip = trip, {}, {})
    }

    var addChickListResponse by mutableStateOf<updateTripResponse>(Response.Success(false))
        private set

    fun addChickList(checkList: List<String>, id: String) {
        viewModelScope.launch {
            try {
                addChickListResponse = Response.Loading
                addChickListResponse = tripsUseCase.chickList(checkList, id)

            } catch (
                e: Exception
            ) {
                addChickListResponse =
                    Response.Failure(e)
            }
        }
    }

    var addPlacesResponse by mutableStateOf<updateTripResponse>(Response.Success(false))
        private set

    fun savePlaces(placeName: String, placeId: String, placePhoto: String, id: String) {

        viewModelScope.launch {
            try {
                addPlacesResponse = Response.Loading
                addPlacesResponse = tripsUseCase.places(
                    placeName, placeId, placePhoto, id
                )

            } catch (
                e: Exception
            ) {
                addPlacesResponse =
                    Response.Failure(e)
            }
        }
    }

    var addPhotoResponse by mutableStateOf<updateTripResponse>(Response.Success(false))
        private set

    fun addPhoto(photo: String, id: String) {

        viewModelScope.launch {
            try {
                addPhotoResponse = Response.Loading
                addPhotoResponse = tripsUseCase.updatePhoto(photo, id)

            } catch (
                e: Exception
            ) {
                addPhotoResponse =
                    Response.Failure(e)
            }
        }
    }

    private val _searchResult =
        MutableStateFlow<Response<List<DataItem?>>>(Response.Success(listOf()))
    val searchResult: StateFlow<Response<List<DataItem?>>> = _searchResult

    fun getSearchedResult(query: String, language: String, filter: String) {
        viewModelScope.launch {
            val result = useCase.invoke(
                query = query,
                language = language,
                filter = filter
            )
            _searchResult.value = result
        }
    }

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

}