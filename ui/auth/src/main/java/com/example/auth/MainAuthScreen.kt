package com.example.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auth.login.LoginScreen
import com.example.auth.signup.SignUpScreen
import com.example.auth.start.StartAuthScreen
import com.example.data.viewmodels.AuthViewModel
import com.example.domain.util.Resource
import com.example.home.HomeScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MainAuthScreen(
    viewModel: AuthViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = getStart()
    ) {
        composable(route = "StartAuthScreen") {
            StartAuthScreen(navController)
        }
        composable(route = "SignUpScreen") {
            SignUpScreen(viewModel, navController)
        }
        composable(route = "LoginScreen") {
            LoginScreen(viewModel, navController)
        }
        composable(route = "HomeScreen") {
            HomeScreen(viewModel, navController)
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