package com.mobilebreakero.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import com.mobilebreakero.domain.util.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.Response.Loading
import kotlinx.coroutines.flow.launchIn

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: SearchResultUseCase) : ViewModel() {

    var search by mutableStateOf(SearchState())

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
            search = SearchState(isLoading = true)
            when (dataState) {
                is Success -> {
                    search = SearchState(
                        isLoading = false,
                        items = dataState.data
                    )
                    Log.d("Search", dataState.data.toString())
                }
                is Loading -> {
                    search = SearchState(
                        isLoading = true
                    )
                    Log.d("Search", "Loading..")
                }

                is Failure -> {
                    search = SearchState(
                        isLoading = false,
                        error = dataState.e.toString()
                    )
                    Log.d("Search", dataState.e.toString())
                }
            }
        }.launchIn(viewModelScope)
    }
}