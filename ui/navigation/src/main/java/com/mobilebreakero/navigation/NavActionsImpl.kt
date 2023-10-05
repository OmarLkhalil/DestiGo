package com.mobilebreakero.navigation

import androidx.navigation.NavController
import com.mobilebreakero.common_ui.navigation.NavigationActions

class NavigationActionsImpl(private val navController: NavController) : NavigationActions {
    override fun navigateToTripsScreen() {
        navController.navigate("TripsScreen")
    }

    override fun navigateToHomeScreen() {
        navController.navigate("HomeScreen")
    }

    override fun navigateToScanScreen() {
        navController.navigate("ScanScreen")
    }

    override fun navigateToProfileScreen() {
        navController.navigate("ProfileScreen")
    }

}
