package com.example.profiledetails.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.repo.addPostResponse
import com.mobilebreakero.domain.repo.postResponse
import com.mobilebreakero.domain.repo.updatePostResponse
import com.mobilebreakero.domain.usecase.firestore.PostUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val useCase: PostUseCase
): ViewModel() {

    private val _postsFlow = MutableStateFlow<postResponse>(Response.Loading)
    val postsFlow: StateFlow<postResponse> get() = _postsFlow

    var postsResult by mutableStateOf(listOf<Post>())

    fun getPosts(userId: String) {
        viewModelScope.launch {
            try {
                val result = useCase.getPostsByUserId(userId)
                if (result is Response.Success) {
                    val posts = result.data
                    postsResult = posts
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
                updateLikesResponse = useCase.likePost(postId, likes, userId, context)
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
                    useCase.sharePost(postId = postId, userId = userId, userName = userName)
            } catch (e: Exception) {
                sharePostResponse = Response.Failure(e)
            }
        }
    }
}