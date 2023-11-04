package com.mobilebreakero.search.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.search.SearchViewModel
import com.mobilebreakero.search.components.MapView
import com.mobilebreakero.search.components.SearchResultsList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.paging.compose.collectAsLazyPagingItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    var searchType by remember { mutableStateOf("") }
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    val coroutineScope = rememberCoroutineScope { ioDispatcher }

    var selectedLocation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        MapView(selectedLocation = selectedLocation) { newLocation ->
            selectedLocation = newLocation
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = searchType,
            onValueChange = {
                searchType = it
            },
            label = { Text("Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            coroutineScope.launch {
                searchViewModel.getSearchResultStream(
                    location = "30.046934647400306,31.289929524064064",
                    keyword = searchText,
                    type = searchType,
                    radius = 10000,
                    language = "en"
                )
            }
        }) {
            Text(text = "Search")
        }

        Spacer(modifier = Modifier.height(16.dp))
        SearchResultsList()
    }
}
