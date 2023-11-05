package com.mobilebreakero.profile.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilebreakero.profile.component.ProfileSection


@Composable
fun ProfileScreen() {
    Column {
        ProfileSection()
    }
}
