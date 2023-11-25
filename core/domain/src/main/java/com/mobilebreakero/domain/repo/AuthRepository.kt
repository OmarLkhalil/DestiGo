package com.mobilebreakero.domain.repo


import android.content.Context
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.flow.StateFlow


typealias AuthStateResponse = StateFlow<Boolean>
typealias SignInResponse = Response<Boolean>
typealias SignUpResponse = Response<Boolean>
typealias SignOutResponse = Response<Boolean>
typealias SendEmailVerificationResponse = Response<Boolean>
typealias SendPasswordResetEmailResponse = Response<Boolean>
typealias ReloadUserResponse = Response<Boolean>
typealias ResetPasswordResponse = Response<Boolean>
typealias SendResetPasswordResponse = Response<Boolean>
typealias UpdateEmailResponse = Response<Boolean>
typealias CheckUserSignedIn = Response<Boolean>
typealias DeleteAccountResponse = Response<Boolean>

interface AuthRepository {

    val currentUser: FirebaseUser?
    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun signInAnonymously(): SignInResponse
    suspend fun sendEmailVerification(): SendEmailVerificationResponse
    suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResponse
    suspend fun signUpWithEmailAndPassword(name: String, email: String, password: String): SignUpResponse
    suspend fun signOut(): SignOutResponse
    suspend fun reloadFirebaseUser(): ReloadUserResponse
    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse
    suspend fun resetPassword(password: String): ResetPasswordResponse
    suspend fun sendResetPassword(email: String, confirmationCode: Int): SendResetPasswordResponse
    suspend fun updateEmail(email: String): UpdateEmailResponse
    suspend fun checkUserSignedIn(email: String, password: String, context : Context): CheckUserSignedIn
    suspend fun deleteAccount(): DeleteAccountResponse
}