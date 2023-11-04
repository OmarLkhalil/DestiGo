package com.mobilebreakero.search.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.mobilebreakero.domain.model.PlaceItem
import com.mobilebreakero.search.SearchViewModel

@Composable
fun SearchResultsList(
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val items = viewModel.searchItems.value.items?.flow?.collectAsLazyPagingItems()
    LazyColumn {
        if (items != null) {
            items(items.itemCount) { index->
                SearchResultItem(
                    item = items,
                    itemIndex = index
                )
            }
        }
    }
}