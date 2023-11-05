package com.mobilebreakero.interestedplaces

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class InterestedPlacesViewModel @Inject constructor(private val useCase: SearchResultUseCase) : ViewModel() {

    var search = mutableStateOf(SearchState())

    val searchItems: State<SearchState>
        get() = search

    fun getSearchResultStream(
        location: String,
        radius: Int,
        type: String,
        language: String,
        keyword: String
    ) {
        useCase(
            location = location,
            radius = radius,
            type = type,
            language = language,
            keyword = keyword
        ).onEach { dataState ->
            when (dataState) {
                is Response.Success -> {
                    search.value = SearchState(
                        items = dataState.data
                    )
                    Log.e("Search", dataState.data.toString())
                }

                is Response.Failure -> {
                    search.value = SearchState(
                        error = dataState.e.message ?: "An unexpected error happened"
                    )
                    Log.e("Search", dataState.e.message ?: "An unexpected error happened")
                }

                is Response.Loading -> {
                    search.value = SearchState(isLoading = true)
                    Log.e("Search", "Loading..")
                }
            }
        }.launchIn(viewModelScope)
    }

}