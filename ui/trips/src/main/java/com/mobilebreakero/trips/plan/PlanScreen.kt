package com.mobilebreakero.trips.plan


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CREATE_TRIP
import com.mobilebreakero.trips.commaon.CreateTripButton

@Composable
fun PlanScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
        , horizontalAlignment = CenterHorizontally
        , verticalArrangement = Arrangement.Center
    ) {

        Text(text = "There's no Trips saved",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold )
        Spacer(modifier = Modifier.height(140.dp))
        CreateTripButton(text = "Create a Trip Plan ",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier.align(CenterHorizontally).height(50.dp).width(300.dp),
            onClick = {navController.navigate(CREATE_TRIP)}
        )
    }
}