package com.mobilebreakero.auth.ui.login.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.auth.ui.login.components.SignInWithEmailAndPasswordScreenContent


@Composable
fun LoginScreen (navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInWithEmailAndPasswordScreenContent(navController = navController)
        }
    }
}