package com.mobilebreakero.destigo.components

import com.mobilebreakero.home.R

sealed class Destinations(val route: String, val title: String, val icon: Int) {
    object Home : Destinations("HomeScreen", "Home", R.drawable.home)
    object Trips : Destinations("TripsScreen", "Trips",  R.drawable.traveling)
    object Scan : Destinations("ScanScreen", "Scan",  R.drawable.qrcode)
    object Profile : Destinations("ProfileScreen", "Profile",  R.drawable.user)
}
