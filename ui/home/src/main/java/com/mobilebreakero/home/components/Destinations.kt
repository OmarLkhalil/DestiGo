package com.mobilebreakero.home.components

import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SCAN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.home.R

sealed class Destinations(val route: String, val icon: Int) {
    object Home : Destinations(HOME_SCREEN,  R.drawable.home)
    object Trips : Destinations(TRIPS_SCREEN, R.drawable.traveling)
    object Scan : Destinations(SCAN_SCREEN, R.drawable.qrcode)
    object Profile : Destinations(PROFILE_SCREEN,  R.drawable.user)
}
