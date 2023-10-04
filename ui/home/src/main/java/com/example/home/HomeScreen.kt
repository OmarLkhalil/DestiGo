package com.example.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.viewmodels.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel?, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel?.logout()
                    navController.navigate("StartAuthScreen") {
                        popUpTo("HomeScreen") { inclusive = true }
                    }
                }
            ) {
                Text(text = "Log out")
            }
        }
    }
}