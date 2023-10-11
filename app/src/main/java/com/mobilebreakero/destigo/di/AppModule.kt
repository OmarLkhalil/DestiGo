package com.mobilebreakero.destigo.di

import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.usecase.AuthUseCase
import com.mobilebreakero.domain.usecase.CurrentUser
import com.mobilebreakero.domain.usecase.GetAuthState
import com.mobilebreakero.domain.usecase.ReloadUser
import com.mobilebreakero.domain.usecase.SendEmailVerification
import com.mobilebreakero.domain.usecase.SendPasswordResetEmail
import com.mobilebreakero.domain.usecase.SignInWithEmailAndPassword
import com.mobilebreakero.domain.usecase.SignInAnnonymously
import com.mobilebreakero.domain.usecase.SignOut
import com.mobilebreakero.domain.usecase.SignUpWithEmailAndPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUseCases(
        repo: AuthRepository
    ) = AuthUseCase(
        getAuthState = GetAuthState(repo),
        signInWithEmailAndPassword = SignInWithEmailAndPassword(repo),
        signUpWithEmailAndPassword = SignUpWithEmailAndPassword(repo),
        signOut = SignOut(repo),
        SignInAnnonymously = SignInAnnonymously(repo),
        sendEmailVerification = SendEmailVerification(repo),
        sendPasswordResetEmail = SendPasswordResetEmail(repo),
        currentUser = CurrentUser(repo),
        reloadUser = ReloadUser(repo)
    )
}