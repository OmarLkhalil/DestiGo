package com.mobilebreakero.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.model.RecommendedPlaceItem
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.repo.addPostResponse
import com.mobilebreakero.domain.repo.addTripResponse
import com.mobilebreakero.domain.repo.getTripsResponse
import com.mobilebreakero.domain.repo.postDetailsResponse
import com.mobilebreakero.domain.repo.postResponse
import com.mobilebreakero.domain.repo.updatePostResponse
import com.mobilebreakero.domain.usecase.RecommendedPlaceUseCase
import com.mobilebreakero.domain.usecase.RecommendedUseCase
import com.mobilebreakero.domain.usecase.firestore.UserUseCase
import com.mobilebreakero.domain.usecase.firestore.PostUseCase
import com.mobilebreakero.domain.usecase.firestore.TripsUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val userUseCase: UserUseCase,
    private val recommendedUseCase: RecommendedUseCase,
    private val recommendedPlaceUseCase: RecommendedPlaceUseCase,
    private val tripsUseCase: TripsUseCase,
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
                val result = userUseCase.getInterestedPlaces(id)
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


    var userRecommendations by mutableStateOf(listOf<TripsItem?>())
        private set

    fun getRecommendations(userInterests: List<String>) {
        viewModelScope.launch {
            try {
                val result = recommendedUseCase.getRecommendation(userInterests)
                userRecommendations = result
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error getting recommendations: $e")
            }
        }
    }

    var userRecommendationsPlaces by mutableStateOf(listOf<RecommendedPlaceItem?>())
        private set

    fun getRecommendedPlaces(userInterests: List<String>) {
        viewModelScope.launch {
            try {
                val result = recommendedPlaceUseCase.getRecommendationPlaces(userInterests)
                userRecommendationsPlaces = result
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error getting recommendations: $e")
            }
        }
    }

    var savePublicTripsResponse by mutableStateOf<addTripResponse>(Response.Success(false))
        private set

    fun savePublicTrips(trip: TripsItem) {
        viewModelScope.launch {
            try {
                savePublicTripsResponse = Response.Loading
                savePublicTripsResponse = tripsUseCase.savePublicTrips(trip, {}, {})
            } catch (e: Exception) {
                savePublicTripsResponse = Response.Failure(e)
            }
        }
    }

}