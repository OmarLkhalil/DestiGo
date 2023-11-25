package com.mobilebreakero.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobilebreakero.profile.component.ProfileSection


@Composable
fun ProfileScreen(navController: NavController) {
    Column {
        ProfileSection(navController = navController)
    }
}
