package com.mobilebreakero.destigo.di

import com.mobilebreakero.data.mapper.PlacesMapper
import com.mobilebreakero.data.remote.TripApi
import com.mobilebreakero.data.repoimpl.DetailsRepoImplementation
import com.mobilebreakero.data.repoimpl.PhotoRepoImplementation
import com.mobilebreakero.data.repoimpl.SearchPlacesRepoImpl
import com.mobilebreakero.data.repoimpl.SearchResultRepoImpl
import com.mobilebreakero.domain.repo.AuthRepository
import com.mobilebreakero.domain.repo.DetailsRepository
import com.mobilebreakero.domain.repo.FireStoreRepository
import com.mobilebreakero.domain.repo.PhotoRepository
import com.mobilebreakero.domain.repo.PostsRepo
import com.mobilebreakero.domain.repo.SearchRepository
import com.mobilebreakero.domain.repo.SearchResultRepo
import com.mobilebreakero.domain.repo.TripsRepo
import com.mobilebreakero.domain.usecase.DetailsUseCase
import com.mobilebreakero.domain.usecase.PhotoUseCase
import com.mobilebreakero.domain.usecase.SearchPlacesUseCase
import com.mobilebreakero.domain.usecase.SearchResultUseCase
import com.mobilebreakero.domain.usecase.auth.AuthUseCase
import com.mobilebreakero.domain.usecase.auth.CheckUserSignedInUseCase
import com.mobilebreakero.domain.usecase.auth.CurrentUser
import com.mobilebreakero.domain.usecase.auth.DeleteAccount
import com.mobilebreakero.domain.usecase.auth.GetAuthState
import com.mobilebreakero.domain.usecase.auth.ReloadUser
import com.mobilebreakero.domain.usecase.auth.RestPassword
import com.mobilebreakero.domain.usecase.auth.SendEmailVerification
import com.mobilebreakero.domain.usecase.auth.SendPasswordResetEmail
import com.mobilebreakero.domain.usecase.auth.SignInWithEmailAndPassword
import com.mobilebreakero.domain.usecase.auth.SignInAnnonymously
import com.mobilebreakero.domain.usecase.auth.SignOut
import com.mobilebreakero.domain.usecase.auth.SignUpWithEmailAndPassword
import com.mobilebreakero.domain.usecase.auth.UpdateEmail
import com.mobilebreakero.domain.usecase.auth.UpdatePassword
import com.mobilebreakero.domain.usecase.firestore.AddUser
import com.mobilebreakero.domain.usecase.firestore.FireStoreUseCase
import com.mobilebreakero.domain.usecase.firestore.GetUserById
import com.mobilebreakero.domain.usecase.firestore.GetUsers
import com.mobilebreakero.domain.usecase.firestore.UpdateUser
import com.mobilebreakero.domain.usecase.firestore.post.AddPostUseCase
import com.mobilebreakero.domain.usecase.firestore.post.GetPostsUseCase
import com.mobilebreakero.domain.usecase.firestore.post.PostUseCase
import com.mobilebreakero.domain.usecase.firestore.trips.AddTrip
import com.mobilebreakero.domain.usecase.firestore.trips.GetTrips
import com.mobilebreakero.domain.usecase.firestore.trips.TripsUseCase
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
        updatePassword = UpdatePassword(repo),
        updateEmail = UpdateEmail(repo),
        checkUserSignedIn = CheckUserSignedInUseCase(repo),
        deleteAccount = DeleteAccount(repo)
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
    fun provideTripsUseCases(
        repo: TripsRepo
    ) = TripsUseCase(
        getTrips = GetTrips(repo),
        addTrip = AddTrip(repo)
    )

    @Provides
    fun provideSearchRepo(api: TripApi, placesMapper: PlacesMapper): SearchRepository {
        return SearchResultRepoImpl(api, placesMapper)
    }

    @Provides
    fun providePostUseCase(
        repo: PostsRepo
    ) = PostUseCase(
        addPost = AddPostUseCase(repo = repo),
        getPosts = GetPostsUseCase(repo),
    )

    @Provides
    fun provideSearchUseCase(repo: SearchRepository): SearchResultUseCase {
        return SearchResultUseCase(repo)
    }

    @Provides
    fun provideSearchPlacesRepo(api: TripApi): SearchResultRepo {
        return SearchPlacesRepoImpl(api)
    }

    @Provides
    fun provideSearchPlacesUseCase(repo: SearchResultRepo): SearchPlacesUseCase {
        return SearchPlacesUseCase(repo)
    }

    @Provides
    fun provideDetailsUseCase(repo: DetailsRepository): DetailsUseCase {
        return DetailsUseCase(repo)
    }

    @Provides
    fun provideDetailsRepository(api: TripApi): DetailsRepository {
        return DetailsRepoImplementation(api)
    }

    @Provides
    fun providePhotosUseCase(repo: PhotoRepository): PhotoUseCase {
        return PhotoUseCase(repo)
    }

    @Provides
    fun providePhotosRepository(api: TripApi): PhotoRepository {
        return PhotoRepoImplementation(api)
    }
}