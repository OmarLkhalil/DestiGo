package com.mobilebreakero.auth.ui.common.components

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.repo.ReloadUserResponse
import com.mobilebreakero.domain.repo.SignOutResponse
import com.mobilebreakero.domain.repo.getTripsResponse
import com.mobilebreakero.domain.repo.updateTripResponse
import com.mobilebreakero.domain.usecase.auth.AuthUseCase
import com.mobilebreakero.domain.usecase.firestore.TripsUseCase
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: AuthUseCase,
    private val tripsUseCase: TripsUseCase,
) : ViewModel() {

    val currentUser = useCase.currentUser.invoke()?.uid

    init {
        getAuthState()
        getTrips(userId = currentUser ?: "")
    }

    fun getAuthState() = useCase.getAuthState.invoke(viewModelScope)

    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Success(false))
        private set

    var signOutResponse by mutableStateOf<SignOutResponse>(Success(false))
        private set

    fun signOut() = viewModelScope.launch {
        signOutResponse = Loading
        signOutResponse = useCase.signOut.invoke()
    }

    val isEmailVerified get() = useCase.currentUser.invoke()?.isEmailVerified ?: false

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Loading
        reloadUserResponse = useCase.reloadUser()
    }

    var updateTripStatusResponse by mutableStateOf<updateTripResponse>(Success(false))
        private set

    fun isTripFinished(id: String, isFinished: Boolean) {
        viewModelScope.launch {
            try {
                updateTripStatusResponse = Loading
                updateTripStatusResponse = tripsUseCase.isTripFinished(id, isFinished)
            } catch (e: Exception) {
                updateTripStatusResponse = Response.Failure(e)
            }
        }
    }


    private val _tripsFlow = MutableStateFlow<getTripsResponse>(Loading)
    val tripsFlow: StateFlow<getTripsResponse> get() = _tripsFlow

    var tripsResult by mutableStateOf(listOf<Trip>())

    fun getTrips(userId: String) {
        viewModelScope.launch {
            try {
                val result = tripsUseCase.getTrips(userId)
                if (result is Success) {
                    val trips = result.data
                    tripsResult = trips
                    _tripsFlow.value = Success(trips)
                    Log.e("MainVM", "getTrips success: $trips")
                } else {
                    _tripsFlow.value = result
                    Log.e("MainVM", "getTrips loading: $result")
                }
            } catch (e: Exception) {
                _tripsFlow.value = Response.Failure(e)
                Log.e("TripsViewModel", "getTrips error: $e")
            }
        }
    }

}