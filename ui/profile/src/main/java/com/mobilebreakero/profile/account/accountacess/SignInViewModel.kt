package com.mobilebreakero.profile.account.accountacess

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.repo.CheckUserSignedIn
import com.mobilebreakero.domain.repo.DeleteAccountResponse
import com.mobilebreakero.domain.repo.SignInResponse
import com.mobilebreakero.domain.usecase.auth.AuthUseCase
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {

    var signInResponse by mutableStateOf<CheckUserSignedIn>(Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String, context: Context) = viewModelScope.launch {
        signInResponse = Loading
        signInResponse = useCase.checkUserSignedIn(email, password, context)
    }

    var deleteAccountResponse by mutableStateOf<DeleteAccountResponse>(Success(false))
        private set

    fun deleteAccount(email: String) = viewModelScope.launch {
        deleteAccountResponse = Loading
        deleteAccountResponse = useCase.deleteAccount()
    }

}