package com.mobilebreakero.domain.typealiases

import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.util.Response

typealias postResponse = Response<List<Post>>
typealias postDetailsResponse = Response<Post>?
typealias addPostResponse = Response<Boolean>
typealias updatePostResponse = Response<Boolean>
