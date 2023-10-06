package com.mobilebreakero.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.auth.login.LoginScreen
import com.mobilebreakero.auth.signup.SignUpScreen
import com.mobilebreakero.auth.start.StartAuthScreen
import com.mobilebreakero.common_ui.viewmodels.AuthViewModel
import com.mobilebreakero.home.HomeScreen
import com.mobilebreakero.interestedplaces.InterestedPlacesScreen
import com.mobilebreakero.profile.ProfileScreen
import com.mobilebreakero.scan.ScanScreen
import com.mobilebreakero.trips.TripsScreen
import com.mobilebreakero.welcome.WelcomeScreen

private const val TransitionDuration = 600

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    startDestination: Boolean,
    viewModel: AuthViewModel,
    navController: NavHostController
) {


    AnimatedNavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = if(startDestination) "WelcomeScreen" else getStart(),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                tween(TransitionDuration)
            )
        },
        exitTransition = { fadeOut(tween(TransitionDuration)) },
        popEnterTransition = { fadeIn(tween(TransitionDuration)) },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                tween(TransitionDuration)
            )
        }
    ) {
        composable(route = "WelcomeScreen") {
            WelcomeScreen(navController = navController)
        }
        composable(route = "StartAuthScreen") {
            StartAuthScreen(navController = navController)
        }
        composable(route = "SignUpScreen") {
            SignUpScreen(viewModel, navController = navController)
        }
        composable(route = "LoginScreen") {
            LoginScreen(viewModel, navController = navController)
        }
        composable(route = "Home") {
            HomeScreen(navController = navController)
        }
        composable(route = "InterestedPlacesScreen") {
            InterestedPlacesScreen()
        }
        composable(route = "Scan") {
            ScanScreen()
        }
        composable(route = "Profile") {
            ProfileScreen()
        }
        composable(route = "Trips") {
            TripsScreen()
        }
    }
}


private fun getStart(): String {

    val firebaseUser = Firebase.auth.currentUser
    return if (firebaseUser == null) {
        "StartAuthScreen"
    } else {
        "Home"
    }
}