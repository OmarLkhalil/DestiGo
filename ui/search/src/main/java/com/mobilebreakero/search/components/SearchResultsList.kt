package com.mobilebreakero.search.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.domain.model.DataItem
import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.search.SearchViewModel


@Composable
fun SearchResultsList(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {

    val searchResults by viewModel.searchResult.collectAsState()

    Log.d("SearchResultsList", "SearchResultsList: $searchResults")

    val photos by viewModel.photo.collectAsState()
    Log.d("SearchResultsList", "Search photos result : $photos")

    LazyColumn(
        modifier = Modifier.height(400.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (searchResults) {
            is Response.Success -> {
                val results = (searchResults as Response.Success<List<DataItem?>>).data
                val photosResult = photos

                items(results.size) { index ->

                    val photosList =
                        if (photosResult is Response.Success) photosResult.data else listOf()

                    SearchResultItem(
                        item = results,
                        itemIndex = index,
                        navController = navController,
                        photos = photosList
                    )
                }
            }

            is Response.Failure -> {
                Log.d("SearchResultsList", "SearchResultsList: Failure")
            }

            is Response.Loading -> {
                Log.d("SearchResultsList", "SearchResultsList: Loading")
                item {
                    LoadingIndicator()
                }
            }
        }
    }
}
