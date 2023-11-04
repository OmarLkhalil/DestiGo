package com.mobilebreakero.search.screen

import android.provider.CalendarContract
import android.text.style.BackgroundColorSpan
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card

import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilebreakero.search.SearchViewModel
import com.mobilebreakero.search.components.MapView
import com.mobilebreakero.search.components.SearchResultsList
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.ChipColors as ChipColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    var searchText by remember { mutableStateOf("") }
    var searchType by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

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

        SearchBar()
        Spacer(modifier = Modifier.height(16.dp))


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(10.dp)

        ) {
            Text(
                text = "Search in",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }


        TypesCatagory()
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(10.dp)

        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp,
                        )
                    ) {
                        append("Select language ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.W100,
                            fontSize = 21.sp,
                        )
                    ) {
                        append("(optional)")
                    }
                }

            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(10.dp)

        ) {
            Text(
                text = "Hotels in Cairo",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LanguageSelection()

        Spacer(modifier = Modifier.height(16.dp))
        SearchResultsList(searchResults = searchViewModel.search.items?.results)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    TextField(
        value = searchText,
        onValueChange = {
            searchText = it
        },
        label = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(9.dp)),

        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    ImageVector = Icons.Filled.Search,
                    contentDescription = "search icon"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypesCatagory() {
    val items = listOf(
        ListItem("Hotels"),
        ListItem("Geos"),
        ListItem("Restaurants"),
        ListItem("Attractions"),
    )
    var selected = remember { mutableStateListOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val context = LocalContext.current
    val color = if (isPressed) Color(0xFF4F80FF) else Color.White
    LazyRow() {
        itemsIndexed(items = items, itemContent = { index, item ->

            ElevatedAssistChip(modifier = Modifier
                .padding(8.dp)
                .background(Color.White)
                .height(40.dp)

                .clip(RoundedCornerShape(24.dp)),


                onClick = { selected = !selected },
                colors = ChipColors = Color(color), label = {
                    Text(
                        item.textString, fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                })


        })

    }


}

@Composable
fun LanguageSelection() {
    val items = listOf(

        ListItem("Arabic"),
        ListItem("English"),
    )
    var selected = remember { mutableStateListOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val context = LocalContext.current
    val color = if (isPressed) Color(0xFF4F80FF) else Color.White
    LazyRow() {
        itemsIndexed(items = items, itemContent = { index, item ->

            ElevatedAssistChip(modifier = Modifier
                .padding(8.dp)
                .background(Color.White)
                .height(40.dp)

                .clip(RoundedCornerShape(24.dp)),


                onClick = { selected = !selected },
                colors = ChipColors = Color(color), label = {
                    Text(
                        item.textString, fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                })


        })

    }


}

data class ListItem(

    val textString: String
)


