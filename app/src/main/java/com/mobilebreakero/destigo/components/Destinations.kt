package com.mobilebreakero.destigo.components

import com.mobilebreakero.home.R

sealed class Destinations(val route: String, val icon: Int) {
    object Home : Destinations("Home",  R.drawable.home)
    object Trips : Destinations("Trips", R.drawable.traveling)
    object Scan : Destinations("Scan", R.drawable.qrcode)
    object Profile : Destinations("Profile",  R.drawable.user)
}
