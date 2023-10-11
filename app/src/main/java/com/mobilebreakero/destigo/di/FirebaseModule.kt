package com.mobilebreakero.destigo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.data.repoimpl.AuthRepositoryImpl
import com.mobilebreakero.domain.repo.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun providesAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

}