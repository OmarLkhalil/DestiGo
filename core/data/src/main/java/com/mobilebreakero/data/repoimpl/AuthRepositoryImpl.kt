package com.mobilebreakero.data.repoimpl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.ReloadUserResponse
import com.mobilebreakero.domain.repo.ResetPasswordResponse
import com.mobilebreakero.domain.repo.SendResetPasswordResponse
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import java.util.Properties
import javax.mail.Message
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

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

    override suspend fun resetPassword(password: String): ResetPasswordResponse {
        return try {
            auth.currentUser?.updatePassword(password)?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun sendResetPassword(
        email: String,
        confirmationCode: Int
    ): SendResetPasswordResponse {
        return try {
            val host = "smtp.gmail.com"
            val port = 587
            val username = "destigo6@gmail.com"
            val password = "qkehaeuxwqkjewsa"

            val props = Properties()
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"
            props["mail.smtp.host"] = host
            props["mail.smtp.port"] = port

            val session = Session.getDefaultInstance(props, null)

            val message = MimeMessage(session)
            message.setFrom(InternetAddress(username))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
            message.subject = "Resetting Password for DestiGo Application"
            message.setText(
                """
            Hello,
            
            Someone has requested to reset the password.
            
            Here's your confirmation code: $confirmationCode
            
            If you did not request it, please ignore this message.
            """.trimIndent()
            )

            val transport = session.getTransport("smtp")

            val result = withContext(Dispatchers.IO) {
                transport.connect(host, username, password)
                transport.sendMessage(message, message.allRecipients)
                transport.close()
                Success(true)
            }

            result
        } catch (e: Exception) {
            Failure(e)
        }
    }

}