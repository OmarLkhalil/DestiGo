package com.mobilebreakero.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.DataItem
import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.usecase.PhotoUseCase
import com.mobilebreakero.domain.usecase.SearchPlacesUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: SearchPlacesUseCase,
    private val photoUseCase: PhotoUseCase
) : ViewModel() {

    private val _searchResult = MutableStateFlow<Response<List<DataItem?>>>(Response.Success(listOf()))
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
    private val _photo = MutableStateFlow<Response<List<PhotoDataItem?>>>(Response.Success(listOf()))
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
