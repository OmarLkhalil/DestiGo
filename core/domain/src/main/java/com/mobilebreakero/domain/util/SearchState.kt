package com.mobilebreakero.domain.util

import androidx.paging.Pager
import com.mobilebreakero.domain.model.PlaceItem


data class SearchState(
    val isLoading: Boolean = false,
    val items: Pager<Int, PlaceItem>? = null,
    val error: String = ""
)
