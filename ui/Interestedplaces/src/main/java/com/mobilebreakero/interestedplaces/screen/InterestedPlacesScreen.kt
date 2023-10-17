package com.mobilebreakero.interestedplaces.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilebreakero.interestedplaces.components.GreetingSection
import com.mobilebreakero.interestedplaces.components.VerticalGrid

@Composable
fun InterestedPlacesScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        GreetingSection()
        VerticalGrid()
    }
}