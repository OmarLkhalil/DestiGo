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
import com.mobilebreakero.auth.ui.login.screens.LoginScreen
import com.mobilebreakero.auth.ui.signup.screens.SignUpScreen
import com.mobilebreakero.auth.ui.start.screen.StartAuthScreen
import com.mobilebreakero.auth.ui.verification.EmailVerificationScreen
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.WELCOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.START_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.INTERESTED_PLACES_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_IN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_UP_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SCAN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.EMAIL_VERIFICATION_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PROFILE_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN
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
    navController: NavHostController
) {

    AnimatedNavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = if(startDestination) WELCOME_SCREEN else START_SCREEN,
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
        composable(route = WELCOME_SCREEN) {
            WelcomeScreen(navController = navController)
        }
        composable(route = START_SCREEN) {
            StartAuthScreen(navController = navController)
        }
        composable(route = EMAIL_VERIFICATION_SCREEN) {
            EmailVerificationScreen(navController = navController)
        }
        composable(route = SIGN_UP_SCREEN) {
            SignUpScreen(navController = navController)
        }
        composable(route = SIGN_IN_SCREEN) {
            LoginScreen()
        }
        composable(route = HOME_SCREEN) {
            HomeScreen(navController = navController)
        }
        composable(route = INTERESTED_PLACES_SCREEN) {
            InterestedPlacesScreen()
        }
        composable(route = SCAN_SCREEN) {
            ScanScreen()
        }
        composable(route = PROFILE_SCREEN) {
            ProfileScreen()
        }
        composable(route = TRIPS_SCREEN) {
            TripsScreen()
        }
    }
}