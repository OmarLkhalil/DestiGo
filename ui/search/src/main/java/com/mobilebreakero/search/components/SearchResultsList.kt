package com.mobilebreakero.search.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.mobilebreakero.domain.model.Place

@Composable
fun SearchResultsList(searchResults: List<Place?>?) {
    LazyColumn {
        items(searchResults?.size ?: 0) { index ->
            searchResults?.get(index)?.let { searchResult ->
                SearchResultItem(searchResult)
            }
        }
    }
}