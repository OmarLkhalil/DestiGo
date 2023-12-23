package com.mobilebreakero.data



data class TextSearchRequest(
    val textQuery: String,
    val includedType: String,
    val languageCode: String
)