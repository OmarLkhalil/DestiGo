package com.mobilebreakero.profile.account.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.domain.repo.updateUserResponse
import com.mobilebreakero.domain.repo.userResponse
import com.mobilebreakero.domain.usecase.firestore.FireStoreUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileSettingsViewModel @Inject constructor(
    private val fireStoreUseCase: FireStoreUseCase,
    private val useCase: FireStoreUseCase
) : ViewModel() {

    var updateProfilePhoto by mutableStateOf<updateUserResponse>(Response.Success(false))
        private set

    fun updatePhoto(id: String, photoUrl: String) {
        viewModelScope.launch {
            updateProfilePhoto = Response.Loading
            updateProfilePhoto = fireStoreUseCase.updateUserPhotoUrl(id = id, photoUrl = photoUrl)
        }
    }

    var updateUserStatus by mutableStateOf<updateUserResponse>(Response.Success(false))
        private set

    fun updateStatus(id: String, status: String) {
        viewModelScope.launch {
            updateUserStatus = Response.Loading
            updateUserStatus = fireStoreUseCase.updateUserStatus(id = id, status = status)
        }
    }

    var updateUserLocation by mutableStateOf<updateUserResponse>(Response.Success(false))
        private set

    fun updateLocation(id: String, location: String) {
        viewModelScope.launch {
            updateUserLocation = Response.Loading
            updateUserLocation = fireStoreUseCase.updateUserLocation(id = id, location = location)
        }
    }
}