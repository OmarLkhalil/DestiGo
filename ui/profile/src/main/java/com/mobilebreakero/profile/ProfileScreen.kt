package com.mobilebreakero.profile

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(){

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Center
    ) {
        Text("ProfileScreen")
    }
}