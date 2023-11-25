package com.mobilebreakero.common_ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobilebreakero.common_ui.R
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SCAN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_IN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_UP_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.START_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.WELCOME_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestiGoTopAppBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val noTopBarList = mutableListOf(
        SIGN_IN_SCREEN, SIGN_UP_SCREEN, START_SCREEN, WELCOME_SCREEN
    )

    if (currentRoute !in noTopBarList) {
        TopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(),
            title = {
                TopBarTitle(navController = navController)
            },
            navigationIcon = {
                NavIcon(navController = navController)
            }
        )
    }

}

@Composable
fun NavIcon(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val routeNames = listOf(HOME_SCREEN, TRIPS_SCREEN, PROFILE_SCREEN, SCAN_SCREEN)

    if (currentRoute !in routeNames) {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.backbutton),
                contentDescription = "back",
                tint = Color(0xFF4F80FF),
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
fun TopBarTitle(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != null) {
        Text(
            text = if (currentRoute.isEmpty()) {
                ""
            } else {
                currentRoute.toString()
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4F80FF),
            fontSize = 22.sp,
            overflow = TextOverflow.Ellipsis
        )
    }

}