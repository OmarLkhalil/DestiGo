package com.mobilebreakero.trips.plan


import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.navigation.NavigationRoutes
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.trips.commaon.CreateTripButton
enum class TripFilter{
    STUDY,
    BUSINESS,
    VACATION,
    OTHER
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripScreen(
    navController: NavController
) {
    var visitMyBros by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<TripFilter?>(null) }
    var direcrions by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
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
                        //painter = painterResource(id = R.drawable.back),
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

        TextField(value = visitMyBros,
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
        Row (
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ){

            TripFilter.values().forEach { reason->
                FilterChip(
                    modifier = Modifier.size(width = 100.dp, height = 35.dp).padding(4.dp),
                    onClick = { selected = reason },
                    label = {
                        Text(reason.name,
                            modifier = Modifier)
                    },
                    selected = reason == selected)
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
        TextField(value = direcrions,
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
        TextField(value = date,
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
        Spacer(modifier = Modifier.height(120.dp))
        CreateTripButton(text = "Next",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier.align(CenterHorizontally).height(50.dp).width(320.dp),
            onClick = {navController.navigate(NavigationRoutes.PLAN_CHECK_LIST)}
        )


    }
}