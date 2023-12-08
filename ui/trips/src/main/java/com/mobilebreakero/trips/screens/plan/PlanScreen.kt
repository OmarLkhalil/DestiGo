package com.mobilebreakero.trips.screens.plan


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.common_ui.navigation.NavigationRoutes
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.trips.TripsViewModel
import com.mobilebreakero.trips.components.CreateTripButton
import com.mobilebreakero.trips.components.TripItem


@Composable
fun PlanScreen(
    navController: NavController,
    viewModel: TripsViewModel = hiltViewModel()
) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        })

    val trips by viewModel.tripsFlow.collectAsState()

    val tripsResult = viewModel.tripsResult

    viewModel.getTrips(userId = user.value.id ?: "")


    if (tripsResult.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "You have no trips",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(140.dp))
            CreateTripButton(text = "Create a Trip Plan ",
                buttonColor = Color(0xff4F80FF),
                modifier = Modifier
                    .align(CenterHorizontally)
                    .height(50.dp)
                    .width(300.dp),
                onClick = { navController.navigate(NavigationRoutes.CREATE_TRIP) }
            )

        }
    } else LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        when (trips) {

            is Response.Loading -> {

            }

            is Response.Success -> {

                val trips_ = (trips as Response.Success<List<Trip>>).data

                items(trips_.size) { index ->
                    trips_[index].name?.let {
                        trips_[index].location?.let { it1 ->
                            TripItem(
                                imageUri = trips_[index].image,
                                name = it,
                                location = it1,
                                onFavoriteClick = {
                                },
                                onClick = {
                                    navController.navigate(NavigationRoutes.TRIP_DETAILS)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    CreateTripButton(text = "add another Trip Plan ",
                        buttonColor = Color(0xff4F80FF),
                        modifier = Modifier
                            .height(50.dp)
                            .width(300.dp),
                        onClick = { navController.navigate(NavigationRoutes.CREATE_TRIP) }
                    )
                }
            }

            else -> {

            }
        }

    }
}