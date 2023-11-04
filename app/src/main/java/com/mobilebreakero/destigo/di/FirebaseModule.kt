package com.mobilebreakero.destigo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.data.repoimpl.AuthRepositoryImpl
import com.mobilebreakero.data.repoimpl.FireStoreRepoImpl
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.FireStoreRepository
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

    @Provides
    fun provideFireStore() = FirebaseFirestore.getInstance()

    @Provides
    fun providesFireStoreRepository(fireStore: FirebaseFirestore): FireStoreRepository =
        FireStoreRepoImpl(fireStore.collection("users"))
}