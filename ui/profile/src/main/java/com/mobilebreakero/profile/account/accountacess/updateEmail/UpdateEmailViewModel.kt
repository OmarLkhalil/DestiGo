package com.mobilebreakero.profile.account.accountacess.updateEmail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.repo.CheckUserSignedIn
import com.mobilebreakero.domain.repo.UpdateEmailResponse
import com.mobilebreakero.domain.usecase.auth.AuthUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateEmailViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {

    var updateEmailResponse by mutableStateOf<UpdateEmailResponse>(Response.Success(false))
        private set

    fun updateEmail(email: String) = viewModelScope.launch(
        Dispatchers.IO
    ) {
        updateEmailResponse = Response.Loading
        updateEmailResponse = useCase.updateEmail(email)
    }
}