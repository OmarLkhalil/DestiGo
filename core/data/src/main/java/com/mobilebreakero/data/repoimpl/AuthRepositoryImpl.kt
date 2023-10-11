package com.mobilebreakero.data.repoimpl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.ReloadUserResponse
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser get() = auth.currentUser

    override suspend fun sendEmailVerification() = try {
        auth.currentUser?.sendEmailVerification()?.await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        return try {
            auth.currentUser?.reload()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String) = try {
        auth.sendPasswordResetEmail(email).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    override suspend fun signInAnonymously() = try {
        auth.signInAnonymously().await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ) = try {
        auth.createUserWithEmailAndPassword(email, password).await()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun signOut() = try {
        auth.signOut()
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }
}