package com.mobilebreakero.data.dto

import com.google.gson.annotations.SerializedName
import com.mobilebreakero.domain.model.ResultsItem

data class Details(

    @field:SerializedName("result")
    val result: Result? = null,

    @field:SerializedName("html_attributions")
    val htmlAttributions: List<Any?>? = null,

    @field:SerializedName("status")
    val status: String,

    val page: Long,

    val totalPage: Int,

    @field:SerializedName("results")
    val results: List<ResultsItem>,

    )

data class ReviewsItem(

    @field:SerializedName("author_name")
    val authorName: String? = null,

    @field:SerializedName("profile_photo_url")
    val profilePhotoUrl: String? = null,

    @field:SerializedName("original_language")
    val originalLanguage: String? = null,

    @field:SerializedName("author_url")
    val authorUrl: String? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("language")
    val language: String? = null,

    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("time")
    val time: Int? = null,

    @field:SerializedName("translated")
    val translated: Boolean? = null,

    @field:SerializedName("relative_time_description")
    val relativeTimeDescription: String? = null
)

data class PhotosItem(

    @field:SerializedName("photo_reference")
    val photoReference: String? = null,

    @field:SerializedName("width")
    val width: Int? = null,

    @field:SerializedName("html_attributions")
    val htmlAttributions: List<String?>? = null,

    @field:SerializedName("height")
    val height: Int? = null
)

data class Result(

    @field:SerializedName("reviews")
    val reviews: List<ReviewsItem?>? = null,

    @field:SerializedName("photos")
    val photos: List<PhotosItem?>? = null
)
