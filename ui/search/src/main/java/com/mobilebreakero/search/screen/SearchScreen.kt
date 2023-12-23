package com.mobilebreakero.search.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.search.SearchViewModel
import com.mobilebreakero.search.components.SearchResultsList
import com.mobilebreakero.search.components.SearchTextField
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


enum class Filters {
    Hotel, Restaurant, Cafe, Clubs, All
}

enum class Languages {
    Arabic, English
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavController
) {

    var searchText by remember { mutableStateOf("") }
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    val coroutineScope = rememberCoroutineScope { ioDispatcher }
    var selected by remember { mutableStateOf(Filters.All) }
    var languageSelected by remember { mutableStateOf(Languages.English) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        SearchTextField(text = searchText, onValueChange = {
            searchText = it
        }, label = "Search")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Search in", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Filters.values().forEach {
                FilterChip(
                    modifier = Modifier
                        .height(50.dp)
                        .width(140.dp)
                        .padding(4.dp),
                    onClick = {
                        selected = it
                    },
                    label = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(2.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    border = null,
                    selected = it == selected,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF4F80FF),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF8FAFF),
                        disabledContainerColor = Color(0xFFEFEEEE),
                        disabledLabelColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = FilterChipDefaults.filterChipElevation(
                        elevation = 5.dp
                    )
                )
            }
        }

        Text("Select Language", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .width(300.dp)
                .align(CenterHorizontally)
                .padding(4.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Languages.values().forEach {
                FilterChip(
                    modifier = Modifier
                        .height(50.dp)
                        .width(140.dp)
                        .padding(4.dp),
                    onClick = {
                        languageSelected = it
                    },
                    label = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(2.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    border = null,
                    selected = it == languageSelected,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF4F80FF),
                        selectedLabelColor = Color.White,
                        containerColor = Color(0xFFF8FAFF),
                        disabledContainerColor = Color(0xFFEFEEEE),
                        disabledLabelColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = FilterChipDefaults.filterChipElevation(
                        elevation = 5.dp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchButton(text = "Search",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .align(CenterHorizontally)
                .height(50.dp)
                .width(320.dp),
            onClick = {
                coroutineScope.launch {
                    searchViewModel.getSearchedResult(
                        query = searchText,
                        filter = if (selected.name.lowercase() == Filters.All.name) "" else selected.name.lowercase(),
                        language = if (languageSelected.name.lowercase() == "english") "en" else "ar"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(22.dp))

        SearchResultsList(navController = navController)
    }
}


@Composable
fun SearchButton(
    onClick: () -> Unit,
    buttonColor: Color? = Color.White,
    text: String,
    border: BorderStroke? = null,
    textColor: Color? = Color.White,
    modifier: Modifier
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(buttonColor!!),
        border = border
    ) {
        Text(text = text, fontSize = 16.sp, color = textColor!!)
    }
}
