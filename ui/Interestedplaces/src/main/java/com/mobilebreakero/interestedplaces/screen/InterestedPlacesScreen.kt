package com.mobilebreakero.interestedplaces.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.interestedplaces.components.GreetingSection
import com.mobilebreakero.interestedplaces.components.VerticalGrid

@Composable
fun InterestedPlacesScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        GreetingSection()
        VerticalGrid()
        Button(
            onClick = { navController.navigate("Home") },
            colors = ButtonDefaults.buttonColors(Color(0xff4F80FF)),
            modifier = Modifier
                .shadow(elevation = 0.dp, shape = CircleShape)
                .width(270.dp)
                .height(50.dp)
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 2.dp)
        ) {
            Text(text = "Next", fontSize = 16.sp, color = Color.White)
        }
    }
}