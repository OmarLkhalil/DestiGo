package com.mobilebreakero.domain.model

import android.net.Uri

data class Post (
    var id: String? = null,
    var userId: String? = null,
    var userName: String?= null,
    var location: String? = "Cairo, Egypt",
    var numberOfLikes: Int? = 0,
    var comments: List<String>? = null,
    var image: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "posts"
    }
}