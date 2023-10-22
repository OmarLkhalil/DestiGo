package com.mobilebreakero.domain.util

import com.mobilebreakero.domain.model.SearchModel

data class SearchState(
    val isLoading: Boolean = false,
    val items: SearchModel ? = null,
    val error: String = ""
)