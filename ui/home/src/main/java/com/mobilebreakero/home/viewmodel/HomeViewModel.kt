package com.mobilebreakero.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.repo.postResponse
import com.mobilebreakero.domain.usecase.firestore.post.PostUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postUseCase: PostUseCase
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
                    Log.e("A7A122", posts.size.toString())
                } else {
                    // Handle other cases if needed
                    _postsFlow.value = result
                    Log.e("A7A123", result.toString())
                }
            } catch (e: Exception) {
                // Handle the exception as needed
                Log.e("HomeViewModel", "Error fetching posts", e)
                _postsFlow.value = Response.Failure(e)
            }
        }
    }
}