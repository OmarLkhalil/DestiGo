package com.mobilebreakero.destigo.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.data.repoimpl.AuthRepositoryImpl
import com.mobilebreakero.data.repoimpl.FireStoreRepoImpl
import com.mobilebreakero.data.repoimpl.PostRepoImpl
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.PostsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun providesAuthRepository(auth: FirebaseAuth, repository: FireStoreRepository): AuthRepository =
        AuthRepositoryImpl(auth, repository)

    @Provides
    fun providesFireStoreRepository(fireStore: FirebaseFirestore): FireStoreRepository =
        FireStoreRepoImpl(fireStore.collection("users"))

    @Provides
    fun providePostRepo(fireStore: FirebaseFirestore): PostsRepo =
        PostRepoImpl(fireStore.collection("posts"))

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}