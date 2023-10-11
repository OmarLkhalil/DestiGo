package com.mobilebreakero.auth.ui.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.repo.SignInResponse
import com.mobilebreakero.domain.usecase.AuthUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel(){

    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set

    init {
        getAuthState()
    }

    fun getAuthState() = useCase.getAuthState(viewModelScope)

    val isEmailVerified get() = useCase.currentUser.invoke()?.isEmailVerified ?: false

    fun signInAnnonymously() = viewModelScope.launch {
        signInResponse = Response.Loading
        signInResponse = useCase.SignInAnnonymously()
    }
}