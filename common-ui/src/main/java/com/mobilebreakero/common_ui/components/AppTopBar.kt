package com.mobilebreakero.common_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavController, route: String) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(route = route)
                }
            ) {
                Box(modifier = Modifier
                    .size(35.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color(0xFF4F80FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        //painter = painterResource(id = R.drawable.back),
                        Icons.Filled.ArrowBack,
                        contentDescription = "back to home",
                        modifier = Modifier
                            .size(25.dp),
                        tint = Color.White
                    )
                }
            }
        },
        modifier = Modifier.shadow(12.dp)
    )
}