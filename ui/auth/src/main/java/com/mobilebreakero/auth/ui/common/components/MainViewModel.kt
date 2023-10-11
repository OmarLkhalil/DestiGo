package com.mobilebreakero.auth.ui.common.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.repo.ReloadUserResponse
import com.mobilebreakero.domain.repo.SignOutResponse
import com.mobilebreakero.domain.usecase.AuthUseCase
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {

    init {
        getAuthState()
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

}