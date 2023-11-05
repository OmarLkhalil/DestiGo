package com.mobilebreakero.trips.screens.plan


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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.navigation.NavigationRoutes
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.trips.components.CreateTripButton


enum class TripFilters {
    Study, Work, Vacation, Adventure, Other
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripScreen(
    navController: NavController
) {
    var visitMyBros by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<TripFilters?>(null) }
    var direcrions by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TopAppBar(title = {
            Text(
                text = "Create Trip", fontSize = 25.sp, fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.navigate(TRIPS_SCREEN)
            }) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF4F80FF)), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "back to home",
                        modifier = Modifier.size(25.dp),
                        tint = Color.White
                    )
                }
            }
        }, modifier = Modifier.shadow(12.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

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
            placeholder = { Text(text = "visit my bros") },
            modifier = Modifier
                .align(Start)
                .width(420.dp)
                .wrapContentHeight()
                .padding(12.dp, 8.dp, 12.dp, 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFEFEEEE),
            ),
            maxLines = 2
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
                        containerColor = Color(0xFFEFEEEE),
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
        TextField(
            value = direcrions,
            onValueChange = {
                direcrions = it
            },
            placeholder = { Text(text = "Cairo,Egypt") },
            modifier = Modifier
                .align(Start)
                .width(420.dp)
                .wrapContentHeight()
                .padding(12.dp, 8.dp, 12.dp, 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFEFEEEE),
            ),
            maxLines = 1
        )
        Text(
            text = "When? ",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(12.dp)
        )
        TextField(
            value = date,
            onValueChange = {
                date = it
            },
            placeholder = { Text(text = "07/10/2023") },
            modifier = Modifier
                .align(Start)
                .width(420.dp)
                .wrapContentHeight()
                .padding(12.dp, 8.dp, 12.dp, 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFEFEEEE),
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(80.dp))
        CreateTripButton(text = "Next",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .align(CenterHorizontally)
                .height(50.dp)
                .width(320.dp),
            onClick = { navController.navigate(NavigationRoutes.PLAN_CHECK_LIST) }
        )
    }
}