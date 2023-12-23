package com.mobilebreakero.details.publicTrips

import android.graphics.drawable.Icon
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.components.calculateEndDate
import com.mobilebreakero.details.DateTransformation
import com.mobilebreakero.details.DetailsViewModel
import com.mobilebreakero.details.R
import com.mobilebreakero.details.components.ElevatedButton
import com.mobilebreakero.details.components.ItemsChip
import com.mobilebreakero.details.components.PlacesToVisit
import com.mobilebreakero.details.components.TripCheckList
import com.mobilebreakero.details.components.TripDetailsCard
import com.mobilebreakero.details.components.TripImages
import com.mobilebreakero.details.components.TripJournal
import com.mobilebreakero.details.formatDate
import com.mobilebreakero.details.loadProgress
import com.mobilebreakero.details.uploadImageToStorage
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.launch


@Composable
fun PublicTripDetails(
    tripId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(tripId) {
        viewModel.getPublicTripDetails(tripId)
    }

    val publicTrip by viewModel.publicTripDetailsResult.collectAsState()

    when (publicTrip) {
        is Response.Loading -> {
            LoadingIndicator()
        }

        is Response.Failure -> {
        }

        is Response.Success -> {
            PublicTripDetails(
                trip = (publicTrip as Response.Success<TripsItem>).data,
                navController = navController
            )
        }
    }

}

@Composable
fun PublicTripDetails(
    trip: TripsItem,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    var isUpdatingTrip by remember { mutableStateOf(false) }
    var tripNameUpdate by remember { mutableStateOf("") }
    var tripDaysUpdate by remember { mutableStateOf("") }
    var tripDateUpdate by remember { mutableStateOf("") }

    TripDetailsCard(title = trip.title ?: "Trip details", onEditClick = {
        isUpdatingTrip = true
    }, content = {

        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }

        var currentProgress by remember { mutableFloatStateOf(0f) }
        var newPhotoUrl by remember { mutableStateOf(trip.image) }
        var imageLink by remember { mutableStateOf("") }
        var isUploading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .align(Alignment.TopCenter)
                        .clip(RoundedCornerShape(5.dp))
                ) {
                    SubcomposeAsyncImage(
                        model = R.drawable.travelcover,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        loading = { LoadingIndicator() })
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    ItemsChip(title = "From ${trip.fullJourney?.startDate ?: ""}") {

                    }
                    var endDate by remember { mutableStateOf("") }
                    var newDays by remember { mutableStateOf("") }


                    if (tripDateUpdate.isNotEmpty())
                        LaunchedEffect(tripDateUpdate) {
                            endDate = calculateEndDate(trip.fullJourney?.startDate ?: "", newDays)
                        }
                    else
                        endDate = trip.fullJourney?.endDate ?: ""

                    ItemsChip(title = "To $endDate") {
                    }
                }

                Text(
                    text = "Places to visit",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Here's a list of places you can visit during your trip.",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                PublicPlacesToVisit(trip = trip, navController = navController)
                Spacer(modifier = Modifier.height(10.dp))

                PublicThingsToDo(trip = trip, navController = navController)
            }
        }

        if (isUpdatingTrip) {
            AlertDialog(
                onDismissRequest = {
                    isUpdatingTrip = false
                },
                title = { Text("Update Trip") },
                text = {
                    Column(
                        verticalArrangement = spacedBy(10.dp),
                    ) {
                        OutlinedTextField(
                            value = tripNameUpdate,
                            onValueChange = { tripNameUpdate = it },
                            label = { Text("New trip name") }
                        )
                        OutlinedTextField(
                            value = tripDateUpdate,
                            visualTransformation = DateTransformation(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = { tripDateUpdate = it },
                            label = { Text("New trip start date") }
                        )
                        OutlinedTextField(
                            value = tripDaysUpdate,
                            onValueChange = { tripDaysUpdate = it },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text("New trip stay days ") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F80FF)),
                        onClick = {
                            if (tripNameUpdate.isNotEmpty()) {
                                viewModel.updateTripName(tripNameUpdate, trip.tripId)
                            }
                            if (tripDateUpdate.isNotEmpty()) {
                                val formattedDate = formatDate(tripDateUpdate)
                                if (formattedDate != null) {
                                    viewModel.updateTripDate(formattedDate, trip.tripId)
                                }
                            }
                            if (tripDaysUpdate.isNotEmpty()) {
                                viewModel.updateTripDays(tripDaysUpdate, trip.tripId)
                            }
                            isUpdatingTrip = false
                        }
                    ) {
                        Text("Update")
                    }
                }
            )
        }
    })
}

@Composable
fun PublicThingsToDo(trip: TripsItem, navController: NavController) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(500.dp),
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.travel_activitites)
                .crossfade(true)
                .build(),
            contentDescription = "Travel",
            modifier = Modifier
                .fillMaxSize(),
            loading = { LoadingIndicator() },
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        ) {
            Text(
                text = "Discover the art of \nperfectly orchestrated journey! 🌟",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 1.2.em,
                modifier = Modifier
                    .padding(10.dp)
            )

            Text(
                text = "From blissful sleep to delightful dining and captivating activities," +
                        " every detail is meticulously calculated." +
                        " Your full journey is not just prepared; it's a masterpiece ready to unfold. ✨🌍",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(10.dp)
            )

        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "What can you do in ${trip.title} ? ",
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier.padding(5.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(trip.image)
                .crossfade(true)
                .build(),
            contentDescription = "Travel",
            modifier = Modifier
                .fillMaxSize(),
            loading = { LoadingIndicator() },
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        ) {
            Text(
                text = "${trip.reasonForTravel}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 1.2.em,
                modifier = Modifier
                    .padding(10.dp)
            )

            trip.description?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Here's a list of things you can do during your trip.",
        fontSize = 18.sp,
        color = Color.Black,
        modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 3.dp)
    )
    LazyColumn(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
    ) {
        items(trip.fullJourney?.activities?.size ?: 0) { index ->
            val thing = trip.fullJourney?.activities?.get(index)
            if (thing != null) {
                ItemsChip(title = thing) {
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Eating time? 🍽️",
        fontSize = 18.sp,
        color = Color.Black,
        modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 3.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(400.dp),
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.paries)
                .crossfade(true)
                .build(),
            contentDescription = "Travel",
            modifier = Modifier
                .fillMaxSize(),
            loading = { LoadingIndicator() },
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        ) {
            Text(
                text = "Savor Culinary Delights",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                lineHeight = 1.2.em,
                modifier = Modifier
                    .padding(10.dp)
            )
            Text(
                text = "Indulge in exquisite flavors and culinary masterpieces during your journey in ${trip.title}." +
                        "DestiGo carefully choose eating places ensures that every meal is a celebration of taste and culture.",
                fontSize = 18.sp,
                color = Color.White,
                lineHeight = 1.2.em,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
        ElevatedButton(
            onClick = {
            },
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomCenter),
            title = "Check it",
            icon = R.drawable.search
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "It's Sleep time! 😴",
        fontSize = 18.sp,
        color = Color.Black,
        modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 3.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(400.dp),
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.sleep)
                .crossfade(true)
                .build(),
            contentDescription = "Travel",
            modifier = Modifier
                .fillMaxSize(),
            loading = { LoadingIndicator() },
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        ) {
            Text(
                text = "Do you think about where to sleep? ",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                lineHeight = 1.2.em,
                modifier = Modifier
                    .padding(10.dp)
            )
            Text(
                text = "It's also prepared for you!. \nThe best place to sleep here in  ${trip.title} is ${trip.fullJourney?.sleep}",
                fontSize = 18.sp,
                color = Color.White,
                lineHeight = 1.2.em,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
        ElevatedButton(
            onClick = {
            },
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomCenter),
            title = "Check it",
            icon = R.drawable.search
        )
    }
    Spacer(modifier = Modifier.height(10.dp))

}