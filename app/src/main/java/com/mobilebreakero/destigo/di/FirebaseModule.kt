package com.mobilebreakero.destigo.di

import com.example.data.repoimpl.AuthRepositoryImpl
import com.example.data.repoimpl.FireStoreRepoImpl
import com.example.domain.repo.AuthRepository
import com.example.domain.repo.FireStoreRepo
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideFireStoreRepo(): FireStoreRepo {
        return FireStoreRepoImpl()
    }
}