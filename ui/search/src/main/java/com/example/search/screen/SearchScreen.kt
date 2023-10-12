package com.example.search.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.search.viewmodel.SearchViewModel
import com.mobilebreakero.domain.model.ResultsItem
import com.mobilebreakero.domain.util.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    var searchType by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                searchViewModel.getSearchedResult(it, "hotels")
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val searchResult by searchViewModel.searchResult.collectAsState()
        when (searchResult.status) {
            Status.LOADING -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            Status.SUCCESS -> {
                val list = searchResult.data
                if (!list.isNullOrEmpty()) {
                    SearchResultsList(searchResults = list)
                } else {
                    Text("No results found")
                }
            }

            Status.FAIL -> {
                Text("Search failed")
                Log.e("Error1", searchResult.message ?: "Unknown Error")
            }

            else -> {}
        }
    }
}

@Composable
fun SearchResultsList(searchResults: List<ResultsItem?>?) {
    LazyColumn {
        items(searchResults?.size!!) { result ->
            // Customize this part to display each item in the search results
            Text(text = searchResults[result]?.name!!)
        }
    }
}