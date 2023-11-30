package com.mobilebreakero.trips

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.repo.addTripResponse
import com.mobilebreakero.domain.repo.updateTripResponse
import com.mobilebreakero.domain.usecase.firestore.trips.TripsUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TripsViewModel @Inject constructor(
    private val tripsUseCase: TripsUseCase
) : ViewModel() {

    var trips by mutableStateOf<List<Trip>>(listOf())
        private set

    fun getTrips(id: String) {
        viewModelScope.launch {
            tripsUseCase.getTrips(id).onEach { result ->
                trips = result.data
            }
        }
    }

    private var addTripResponse by mutableStateOf<addTripResponse>(Response.Success(false))
        private set

    fun addTripToFireStore(trip: Trip) = viewModelScope.launch {
        addTripResponse = Response.Loading
        addTripResponse = tripsUseCase.addTrip(trip = trip, {}, {})
    }

    private val _selectedTrips = mutableStateListOf<TempTrip>()
    val selectedTrips: List<TempTrip> get() = _selectedTrips

    fun addSelectedTrip(trip: TempTrip) {
        _selectedTrips.add(trip)
    }
    var addChickListResponse by mutableStateOf<updateTripResponse> (Response.Success(false))
        private set

    fun addChickList(checkList: List<String>,id: String){
        viewModelScope.launch {
            try {
                addChickListResponse = Response.Loading
                addChickListResponse = tripsUseCase.chickList(checkList,id)

            }catch (
                e:Exception
            ){
                addChickListResponse =
                    Response.Failure(e)
            }
        }
    }

}


data class TempTrip (
    var id: String? = null,
    var userId: String? = null,
    var location: String? = null,
    var name: String? = null,
    var date : String? = null,
    var why : String? = null,
    var image: Uri? = null,
    var checkList : List<String>? = null,
    var places : List<String>? = null,
)