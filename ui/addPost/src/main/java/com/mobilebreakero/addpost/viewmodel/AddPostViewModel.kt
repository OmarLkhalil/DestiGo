package com.mobilebreakero.addpost.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.repo.addPostResponse
import com.mobilebreakero.domain.usecase.firestore.post.PostUseCase
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
        Log.e("SuccessPostAwwdd", "Done")
        addPostResponse = Response.Loading
        addPostResponse = postUseCase.addPost(
            post = post, {
                Log.e("SuccessPostAdd", "Done")
            },{
                Log.e("FailedAdddd", "KFNMMCVPS")
            })
    }
}