package com.mobilebreakero.trips.screens.plan


import android.app.DatePickerDialog
import android.net.Uri
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.AuthButton
import com.mobilebreakero.common_ui.components.MapView
import com.mobilebreakero.common_ui.navigation.NavigationRoutes
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.util.DataUtils
import com.mobilebreakero.trips.TempTrip
import com.mobilebreakero.trips.TripsViewModel
import com.mobilebreakero.trips.components.CreateTripButton
import java.lang.Math.random
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


enum class TripFilters {
    Study, Work, Vacation, Adventure, Other
}

var tripId = generateRandomTripId()

fun generateRandomTripId(): Int {
    return (100000..999999).random()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripScreen(
    navController: NavController,
    viewModel: TripsViewModel = hiltViewModel()
) {

    val isDateClicked = remember { mutableStateOf(false) }
    var visitMyBros by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<TripFilters?>(null) }
    var travelReason by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf("Cairo, Egypt") }
    var selectedDate by remember { mutableStateOf("11/12/2023") }
    val isLocationClicked = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val user = DataUtils.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Trip Name",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(8.dp)
        )
        TextField(
            value = visitMyBros,
            onValueChange = {
                visitMyBros = it
            },
            label = {
                Text(text = "Visit my Bros")
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color.Black,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(.7.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                textColor = Color.Black,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        Text(
            text = "Why? ",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(12.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TripFilters.values().forEach {
                FilterChip(
                    modifier = Modifier
                        .height(50.dp)
                        .width(140.dp)
                        .padding(4.dp),
                    onClick = {
                        selected = it
                        travelReason = it.name
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
                    shape = RoundedCornerShape(12.dp),
                )
            }
        }

        Text(
            text = "Where? ",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(12.dp)
        )

        AuthButton(
            onClick = {
                isLocationClicked.value = true
            },
            modifier = Modifier
                .width(300.dp)
                .height(80.dp)
                .padding(12.dp, 12.dp, 20.dp, 12.dp)
                .border(.7.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp)),
            text = selectedLocation,
            buttonColor = Color.White,
            textColor = Color.Black.copy(alpha = 0.3f)
        )
        Text(
            text = "When? ",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(12.dp)
        )

        AuthButton(
            onClick = {
                isDateClicked.value = true
            },
            modifier = Modifier
                .width(350.dp)
                .height(80.dp)
                .padding(12.dp, 12.dp, 20.dp, 12.dp)
                .border(.7.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp)),
            text = selectedDate,
            buttonColor = Color.White,
            textColor = Color.Black.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(80.dp))

        CreateTripButton(text = "Next",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .align(CenterHorizontally)
                .height(50.dp)
                .width(320.dp),
            onClick = {
                viewModel.addTripToFireStore(
                    Trip(
                        id = tripId.toString(),
                        userId = user?.id,
                        why = travelReason,
                        location = selectedLocation,
                        date = selectedDate,
                        name = visitMyBros
                    )
                )
                navController.navigate(NavigationRoutes.PLAN_CHECK_LIST)
            }
        )
    }

    if (isLocationClicked.value) {
        MapView(
            selectedLocation = selectedLocation,
            onLocationSelected = {
                selectedLocation = it
                isLocationClicked.value = false
            },
            context = context
        )
    }

    if (isDateClicked.value) {
        ShowDatePickerDialog(selectedDate = selectedDate, onDateSelected = {
            selectedDate = it
        })
    }

}

@Composable
fun ShowDatePickerDialog(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                    .format(Calendar.getInstance().apply {
                        set(year, month, dayOfMonth)
                    }.time)
                onDateSelected(formattedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            if (datePickerDialog.isShowing) {
                datePickerDialog.dismiss()
            }
        }
    }

    LaunchedEffect(selectedDate) {
        if (!datePickerDialog.isShowing) {
            datePickerDialog.updateDate(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }
}