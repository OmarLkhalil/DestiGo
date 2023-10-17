package com.mobilebreakero.auth.ui.passwordreset.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.auth.ui.passwordreset.components.UpdatePasswordContent

@Composable
fun ChooseNewPasswordScreen(navController: NavController){

    Column (
        modifier = Modifier.fillMaxSize()
    ){
        UpdatePasswordContent(navController = navController)
    }

}