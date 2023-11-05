package com.mobilebreakero.domain.repo

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.util.Response


typealias postResponse = Response<Post>
typealias addPostResponse = Response<Boolean>
typealias updatePostResponse = Response<Boolean>

interface PostsRepo {

    suspend fun addPost(
        post: Post,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ): addPostResponse
}