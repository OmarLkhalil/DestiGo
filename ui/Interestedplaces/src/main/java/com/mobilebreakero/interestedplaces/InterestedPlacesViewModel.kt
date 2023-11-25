package com.mobilebreakero.interestedplaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.PlacesItem
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterestedPlacesViewModel @Inject constructor(private val useCase: SearchResultUseCase) :
    ViewModel() {

    private val _searchResults = MutableStateFlow<Response<List<PlacesItem>>>(Response.Loading)
    val searchResults: StateFlow<Response<List<PlacesItem>>> get() = _searchResults

    fun searchPlaces(location: String, type: String, language: String) {
        viewModelScope.launch {
            _searchResults.value = useCase(location, type, language)
                .catch { exception ->

                }
                .first()
        }
    }
}