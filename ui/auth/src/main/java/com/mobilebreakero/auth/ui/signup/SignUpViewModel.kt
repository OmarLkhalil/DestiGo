package com.mobilebreakero.auth.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.repo.SendEmailVerificationResponse
import com.mobilebreakero.domain.repo.SignUpResponse
import com.mobilebreakero.domain.usecase.AuthUseCase
import com.mobilebreakero.domain.util.Response.Loading
import com.mobilebreakero.domain.util.Response.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel(){

    var signUpResponse by mutableStateOf<SignUpResponse>(Success(false))
        private set

    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Success(false))
        private set

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signUpResponse = Loading
        signUpResponse = useCase.signUpWithEmailAndPassword(email, password)
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Loading
        sendEmailVerificationResponse = useCase.sendEmailVerification()
    }

}