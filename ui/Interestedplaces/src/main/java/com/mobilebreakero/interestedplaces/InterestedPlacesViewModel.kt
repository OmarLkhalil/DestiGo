package com.mobilebreakero.interestedplaces

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.PlacesItem
import com.mobilebreakero.domain.repo.updateUserResponse
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import com.mobilebreakero.domain.usecase.firestore.FireStoreUseCase
import com.mobilebreakero.domain.usecase.firestore.post.PostUseCase
import com.mobilebreakero.domain.usecase.firestore.trips.TripsUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestedPlacesViewModel @Inject constructor(
    private val fireStoreUseCase: FireStoreUseCase
) :
    ViewModel() {


    var updateUserResponse = mutableStateOf<updateUserResponse>(Response.Success(false))
        private set

    fun addInterestedPlacesIntoDatasource(id: String, interestedPlaces: List<String>) {
        viewModelScope.launch {
            try {
                updateUserResponse.value = Response.Loading
                updateUserResponse.value =
                    fireStoreUseCase.updateUserInterestedPlaces(id, interestedPlaces)
            } catch (e: Exception) {
                updateUserResponse.value = Response.Failure(e)
            }
        }
    }

}