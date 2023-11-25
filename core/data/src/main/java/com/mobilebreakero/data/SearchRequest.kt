package com.mobilebreakero.data

import org.json.JSONObject


data class TextSearchRequest(
    val textQuery: String,
    val includedType: String,
    val languageCode: String
)