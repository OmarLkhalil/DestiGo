package com.mobilebreakero.common_ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.domain.model.AppUser
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
class MainViewModel @Inject constructor(
    private val useCase: FireStoreUseCase
) : ViewModel() {



    private val firebaseUser = Firebase.auth.currentUser
    init {
        getUser(firebaseUser?.uid!!)
    }

    fun getUser(id: String): Flow<userResponse> {
        return flow {
            val result = useCase.getUserByID(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}