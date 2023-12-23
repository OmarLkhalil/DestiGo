package com.mobilebreakero.details

import android.util.Log
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.details.components.DetailsCard
import com.mobilebreakero.details.components.ItemsChip
import com.mobilebreakero.details.components.ShowDatePickerDialog
import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.util.Response


@Composable
fun PlacesDetailsTrips(
    locationId: String,
    tripId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        Log.d("Details", "locationId: $locationId")
        Log.d("Details", "tripId: $tripId")
        viewModel.getPhoto(locationId)
        viewModel.getDetails(locationId)
        viewModel.getTripDetailsResult(tripId)
    }

    val photos by viewModel.photo.collectAsState()
    val details by viewModel.detailsResult.collectAsState()

    when (photos) {
        is Response.Success -> {
            val resultsPhotos = (photos as Response.Success<List<PhotoDataItem?>>).data
            when (details) {
                is Response.Success -> {
                    val detailsResponse =
                        (details as Response.Success<DetailsResponse>).data
                    val tripDetails by viewModel.tripDetailsResult.collectAsState()

                    when (tripDetails) {
                        is Response.Success -> {
                            val tripsResults = (tripDetails as Response.Success<Trip>).data
                            PlacesTripDetailsContent(
                                photos = resultsPhotos,
                                detailsResponse = detailsResponse,
                                trip = tripsResults
                            )
                        }

                        is Response.Failure -> {

                        }

                        else -> {
                            Response.Loading
                            LoadingIndicator()
                        }
                    }

                }

                is Response.Failure -> {

                }

                else -> {
                    Response.Loading
                    LoadingIndicator()
                }
            }
        }

        is Response.Failure -> {

        }

        else -> {
            Response.Loading
            LoadingIndicator()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlacesTripDetailsContent(
    photos: List<PhotoDataItem?>,
    detailsResponse: DetailsResponse,
    viewModel: DetailsViewModel = hiltViewModel(),
    trip: Trip
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        photos.size
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
            ) { page ->
                val photo = photos[page]?.images?.large?.url
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = photo,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        loading = { LoadingIndicator() })
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.2f))
                    )
                }
            }

            Row(
                Modifier
                    .height(24.dp)
                    .padding(start = 4.dp)
                    .width(100.dp)
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.Start
            ) {
                repeat(photos.size) { iteration ->
                    val lineWeight = animateFloatAsState(
                        targetValue = if (pagerState.currentPage == iteration) {
                            1.0f
                        } else {
                            if (iteration < pagerState.currentPage) {
                                0.5f
                            } else {
                                0.5f
                            }
                        }, label = "size", animationSpec = tween(300, easing = EaseInOut)
                    )
                    val color =
                        if (pagerState.currentPage == iteration) Color(0xFF4F80FF) else Color.White
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color)
                            .weight(lineWeight.value)
                            .height(10.dp)
                    )
                }
            }
        }

        Text(
            text = detailsResponse.name ?: "",
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = detailsResponse.rankingData?.rankingString ?: "",
            fontSize = 16.sp,
            modifier = Modifier.padding(3.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        DetailsCard(
            title = "Location Details",
            details = detailsResponse.addressObj?.addressString ?: ""
        ) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        DetailsCard(title = "About", details = detailsResponse.description ?: "") {
            Spacer(modifier = Modifier.height(20.dp))
        }
        val isDateClicked = remember { mutableStateOf(false) }

        DetailsCard(title = "Your Saved Details", details = "") {
            val places = trip.places?.size ?: 0

            if (places == 0) {
                Text(text = "No place details added yet", modifier = Modifier.padding(8.dp))
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                items(places) {
                    val tripPlace = trip.places!![it]
                    var selectedDate by remember { mutableStateOf(tripPlace.date ?: "12/12/2023") }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .horizontalScroll(rememberScrollState())
                    ) {
                        ItemsChip(title = selectedDate) {
                            isDateClicked.value = true
                        }
                    }

                    if (isDateClicked.value) {
                        ShowDatePickerDialog(selectedDate = selectedDate, onDateSelected = {
                            selectedDate = it
                            viewModel.updatePlaceDateToVisit(
                                id = tripPlace.tripId ?: "",
                                placeId = tripPlace.id ?: "",
                                date = it
                            )
                            isDateClicked.value = false
                        })
                    }
                }
            }
        }
    }
}