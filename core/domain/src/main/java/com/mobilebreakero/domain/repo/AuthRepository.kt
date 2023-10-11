package com.mobilebreakero.domain.repo

import com.google.firebase.auth.FirebaseUser
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

typealias AuthStateResponse = StateFlow<Boolean>
typealias SignInResponse = Response<Boolean>
typealias SignUpResponse = Response<Boolean>
typealias SignOutResponse = Response<Boolean>
typealias SendEmailVerificationResponse = Response<Boolean>
typealias SendPasswordResetEmailResponse = Response<Boolean>
typealias ReloadUserResponse = Response<Boolean>


interface AuthRepository {

    val currentUser: FirebaseUser?
    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun signInAnonymously(): SignInResponse
    suspend fun sendEmailVerification(): SendEmailVerificationResponse
    suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResponse
    suspend fun signUpWithEmailAndPassword(email: String, password: String): SignUpResponse
    suspend fun signOut(): SignOutResponse
    suspend fun reloadFirebaseUser(): ReloadUserResponse
    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse

}