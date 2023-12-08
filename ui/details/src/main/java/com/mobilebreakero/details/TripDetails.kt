package com.mobilebreakero.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.details.components.DetailsCard
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.util.Response


@Composable
fun TripDetailsScreen(
    tripId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getTripDetailsResult(tripId)
    }

    val tripDetails by viewModel.tripDetailsResult.collectAsState()

    when (tripDetails) {
        is Response.Success -> {
            val results = (tripDetails as Response.Success<Trip>).data
            TripDetails(results)
        }

        is Response.Failure -> {

        }

        else -> {
            Response.Loading
            LoadingIndicator()
        }
    }


}

@Composable
fun TripDetails(trip: Trip) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.TopCenter)
            ) {
                SubcomposeAsyncImage(
                    model = trip.image,
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

        Spacer(modifier = Modifier.height(16.dp))

        DetailsCard(
            title = "trip details",
            details = "Trip Name: ${trip.name} \n Trip Location: ${trip.location} \n Trip Duration: ${trip.duration}"
        ) {

        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

