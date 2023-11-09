package com.mobilebreakero.destigo.di

import com.mobilebreakero.data.mapper.PlacesMapper
import com.mobilebreakero.data.remote.TripApi
import com.mobilebreakero.data.repoimpl.SearchResultRepoImpl
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.PostsRepo
import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import com.mobilebreakero.domain.usecase.auth.AuthUseCase
import com.mobilebreakero.domain.usecase.auth.CurrentUser
import com.mobilebreakero.domain.usecase.auth.GetAuthState
import com.mobilebreakero.domain.usecase.auth.ReloadUser
import com.mobilebreakero.domain.usecase.auth.RestPassword
import com.mobilebreakero.domain.usecase.auth.SendEmailVerification
import com.mobilebreakero.domain.usecase.auth.SendPasswordResetEmail
import com.mobilebreakero.domain.usecase.auth.SignInWithEmailAndPassword
import com.mobilebreakero.domain.usecase.auth.SignInAnnonymously
import com.mobilebreakero.domain.usecase.auth.SignOut
import com.mobilebreakero.domain.usecase.auth.SignUpWithEmailAndPassword
import com.mobilebreakero.domain.usecase.auth.UpdatePassword
import com.mobilebreakero.domain.usecase.firestore.AddUser
import com.mobilebreakero.domain.usecase.firestore.FireStoreUseCase
import com.mobilebreakero.domain.usecase.firestore.GetUserById
import com.mobilebreakero.domain.usecase.firestore.GetUsers
import com.mobilebreakero.domain.usecase.firestore.UpdateUser
import com.mobilebreakero.domain.usecase.firestore.post.AddPostUseCase
import com.mobilebreakero.domain.usecase.firestore.post.PostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAuthUseCases(
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
        reloadUser = ReloadUser(repo),
        resetPassword = RestPassword(repo),
        updatePassword = UpdatePassword(repo)
    )

    @Provides
    fun provideFireStoreUseCases(
        repo: FireStoreRepository
    ) = FireStoreUseCase(
        addUser = AddUser(repo),
        getUsers = GetUsers(repo),
        updateUser = UpdateUser(repo),
        getUserByID = GetUserById(repo),
    )


    @Provides
    fun provideSearchRepo(api: TripApi, placesMapper: PlacesMapper): SearchResultRepo {
        return SearchResultRepoImpl(api, placesMapper)
    }
    @Provides
    fun providePostUseCase(
        repo: PostsRepo
    ) = PostUseCase (
        addPost = AddPostUseCase(repo = repo)
    )

    @Provides
    fun provideSearchUseCase(repo: SearchResultRepo): SearchResultUseCase {
        return SearchResultUseCase(repo)
    }
}