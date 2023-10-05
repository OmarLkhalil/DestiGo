package com.mobilebreakero.destigo.di

import com.google.firebase.auth.FirebaseAuth
import com.mobilebreakero.data.repoimpl.AuthRepositoryImpl
import com.mobilebreakero.data.repoimpl.FireStoreRepoImpl
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.FireStoreRepo
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