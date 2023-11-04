package com.mobilebreakero.auth.ui.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.repo.SendEmailVerificationResponse
import com.mobilebreakero.domain.repo.SignUpResponse
import com.mobilebreakero.domain.usecase.auth.AuthUseCase
import com.mobilebreakero.domain.usecase.firestore.FireStoreUseCase
import com.mobilebreakero.domain.util.Response.Loading
import com.mobilebreakero.domain.util.Response.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val fireStoreUseCase: FireStoreUseCase
) : ViewModel(){

    var signUpResponse by mutableStateOf<SignUpResponse>(Success(false))
        private set

    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Success(false))
        private set

    fun signUpWithEmailAndPassword(name: String, email: String, password: String) = viewModelScope.launch {
        Log.e("Click1", "A7A")
        signUpResponse = Loading
        signUpResponse = authUseCase.signUpWithEmailAndPassword(name, email, password)
    }

//    fun createUserOnFireStore(user: AppUser) = viewModelScope.launch {
//        Log.e("Click3", "A7A")
//        signUpResponse = Loading
//        signUpResponse = fireStoreUseCase.addUser(user)
//    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Loading
        sendEmailVerificationResponse = authUseCase.sendEmailVerification()
    }

}