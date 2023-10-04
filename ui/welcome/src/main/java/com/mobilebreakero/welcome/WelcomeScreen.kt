package com.mobilebreakero.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.welcome.components.WelcomePager

@Composable
fun WelcomeScreen(navController: NavController){

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        WelcomePager(navController = navController)
    }
}