package com.mobilebreakero.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.auth.login.LoginScreen
import com.mobilebreakero.auth.start.StartAuthScreen
import com.mobilebreakero.common_ui.viewmodels.AuthViewModel
import com.mobilebreakero.home.HomeScreen
import com.google.accompanist.navigation.animation.composable
import com.mobilebreakero.auth.signup.SignUpScreen
import com.mobilebreakero.interestedplaces.InterestedPlacesScreen
import com.mobilebreakero.welcome.WelcomeScreen

private const val TransitionDuration = 600

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    startDestination: Boolean,
    viewModel: AuthViewModel,
) {
    val navController = rememberAnimatedNavController()

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
        composable(route = "HomeScreen") {
            HomeScreen(viewModel, navController = navController)
        }
        composable(route = "InterestedPlacesScreen") {
            InterestedPlacesScreen()
        }
    }
}

private fun getStart(): String {

    val firebaseUser = Firebase.auth.currentUser
    return if (firebaseUser == null) {
        "StartAuthScreen"
    } else {
        "HomeScreen"
    }
}