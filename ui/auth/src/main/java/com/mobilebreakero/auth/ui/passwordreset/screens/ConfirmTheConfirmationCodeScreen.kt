package com.mobilebreakero.auth.ui.passwordreset.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.auth.ui.passwordreset.components.ConfirmTheConfirmationCodeScreenContent

@Composable
fun ConfirmTheConfirmationCodeScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ConfirmTheConfirmationCodeScreenContent(navController = navController)
    }
}