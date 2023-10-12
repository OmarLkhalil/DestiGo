package com.example.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.ResultsItem
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import com.mobilebreakero.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchResultUseCase: SearchResultUseCase
) : ViewModel() {
    private val _searchResult = MutableStateFlow<Resource<List<ResultsItem?>>>(Resource.Loading())
    val searchResult: StateFlow<Resource<List<ResultsItem?>>> = _searchResult

    fun getSearchedResult(searchedText: String, searchType: String) {
        viewModelScope.launch {
            // Perform your search and update the _searchResult StateFlow accordingly.
            val result = searchResultUseCase.invoke(searchedText, searchType)
            _searchResult.value = result
        }
    }
}