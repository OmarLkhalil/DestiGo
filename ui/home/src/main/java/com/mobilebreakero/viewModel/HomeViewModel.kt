package com.mobilebreakero.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.repo.addPostResponse
import com.mobilebreakero.domain.repo.getTripsResponse
import com.mobilebreakero.domain.repo.postDetailsResponse
import com.mobilebreakero.domain.repo.postResponse
import com.mobilebreakero.domain.repo.tripsResponseInterested
import com.mobilebreakero.domain.repo.updatePostResponse
import com.mobilebreakero.domain.repo.updateUserResponse
import com.mobilebreakero.domain.usecase.firestore.FireStoreUseCase
import com.mobilebreakero.domain.usecase.firestore.post.PostUseCase
import com.mobilebreakero.domain.usecase.firestore.trips.TripsUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val tripsUseCase: TripsUseCase,
    private val fireStoreUseCase: FireStoreUseCase
) : ViewModel() {

    init {
        getPosts()
    }

    private val _postsFlow = MutableStateFlow<postResponse>(Response.Loading)
    val postsFlow: StateFlow<postResponse> get() = _postsFlow

    fun getPosts() {
        viewModelScope.launch {
            try {
                val result = postUseCase.getPosts()
                if (result is Response.Success) {
                    val posts = result.data
                    _postsFlow.value = Response.Success(posts)
                } else {
                    _postsFlow.value = result
                }
            } catch (e: Exception) {
                _postsFlow.value = Response.Failure(e)
            }
        }
    }


    var updateLikesResponse by mutableStateOf<updatePostResponse>(Response.Success(false))
        private set

    fun likePost(
        postId: String,
        likes: Int,
        userId: String,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                updateLikesResponse = Response.Loading
                updateLikesResponse = postUseCase.likePost(postId, likes, userId, context)
            } catch (e: Exception) {
                updateLikesResponse = Response.Failure(e)
            }
        }
    }

    var sharePostResponse by mutableStateOf<addPostResponse>(Response.Success(false))
        private set

    fun sharePost(postId: String, userId: String, userName: String) {
        viewModelScope.launch {
            try {
                sharePostResponse = Response.Loading
                sharePostResponse =
                    postUseCase.sharePost(postId = postId, userId = userId, userName = userName)
            } catch (e: Exception) {
                sharePostResponse = Response.Failure(e)
            }
        }
    }


    var addCommentResponse by mutableStateOf<updatePostResponse>(Response.Success(false))
        private set

    fun addComment(postId: String, comment: String, userId: String, userName: String) {
        viewModelScope.launch {
            try {
                addCommentResponse = Response.Loading
                addCommentResponse = postUseCase.addComment(
                    id = postId,
                    comment = comment,
                    userId = userId,
                    userName = userName
                )
            } catch (e: Exception) {
                addCommentResponse = Response.Failure(e)
            }
        }
    }

    private val _tripsFlow = MutableStateFlow<getTripsResponse>(Response.Loading)
    val tripsFlow: StateFlow<getTripsResponse> get() = _tripsFlow

    var tripsResult by mutableStateOf(listOf<Trip>())

    fun getTrips(id: String) {
        viewModelScope.launch {
            try {
                val result = fireStoreUseCase.getInterestedPlaces(id)
                if (result is Response.Success) {
                    val trips = result.data
                    tripsResult = trips
                    _tripsFlow.value = Response.Success(trips)
                    Log.e("HomeViewModel", "getTrips: $trips")
                } else {
                    _tripsFlow.value = result
                    Log.e("HomeViewModel", "getTrips: $result")
                }
            } catch (e: Exception) {
                _tripsFlow.value = Response.Failure(e)
                Log.e("HomeViewModel", "getTrips: $e")
            }
        }
    }

    private val details =
        MutableStateFlow<postDetailsResponse>(Response.Success(Post()))
    val detailsResult: StateFlow<postDetailsResponse> = details


    fun getPostDetails(id: String) {
        viewModelScope.launch {
            try {
                details.value = Response.Loading
                val result = postUseCase.getPostDetails(id)
                details.value = result
            } catch (e: Exception) {
                details.value = Response.Failure(e)
            }
        }
    }


    var getUserInterestedPlacesBasedOnDatasource =
        mutableStateOf<tripsResponseInterested>(Response.Success(listOf()))
        private set

    fun getUserInterestedPlaces(id: String) {
        viewModelScope.launch {
            try {
                getUserInterestedPlacesBasedOnDatasource.value = Response.Loading
                getUserInterestedPlacesBasedOnDatasource.value =
                    fireStoreUseCase.getInterestedPlaces(id)
            } catch (e: Exception) {
                getUserInterestedPlacesBasedOnDatasource.value = Response.Failure(e)
            }
        }
    }
}