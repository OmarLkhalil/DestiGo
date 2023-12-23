package com.mobilebreakero.addpost.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.repo.addPostResponse
import com.mobilebreakero.domain.usecase.firestore.PostUseCase
import com.mobilebreakero.domain.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val postUseCase: PostUseCase
) : ViewModel() {

    var addPostResponse by mutableStateOf<addPostResponse>(Response.Success(false))
        private set

    fun addPost(post: Post) = viewModelScope.launch {
        addPostResponse = Response.Loading
        addPostResponse = postUseCase.addPost(post = post, {},{})
    }
}