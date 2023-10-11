package com.mobilebreakero.destigo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mobilebreakero.auth.ui.common.components.MainViewModel
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.EMAIL_VERIFICATION_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.START_SCREEN
import com.mobilebreakero.home.components.BottomNavigation
import com.mobilebreakero.destigo.ui.theme.DestiGoTheme
import com.mobilebreakero.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val FIRST_LAUNCH_PREFS = "FirstLaunchPrefs"
    private val FIRST_LAUNCH_KEY = "FirstLaunch"
    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(FIRST_LAUNCH_PREFS, Context.MODE_PRIVATE)
        val isFirstLaunch = prefs.getBoolean(FIRST_LAUNCH_KEY, true)


        setContent {

            DestiGoTheme {

                val navController = rememberAnimatedNavController()

                Scaffold(
                    bottomBar = { BottomNavigation(navController) }
                ) { pv ->
                    Box(modifier = Modifier.padding(pv)) {
                        MainNavHost(
                            startDestination = isFirstLaunch,
                            navController
                        )
                        AuthState(navController)
                    }
                }
            }
        }

        if (isFirstLaunch) {
            val editor = prefs.edit()
            editor.putBoolean(FIRST_LAUNCH_KEY, false)
            editor.apply()
        }
    }

    @Composable
    private fun AuthState(
        navController: NavController
    ) {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        if (isUserSignedOut) {
            navController.navigate(START_SCREEN)
        } else {
            if (viewModel.isEmailVerified) {
                navController.navigate(HOME_SCREEN)
            } else {
                navController.navigate(EMAIL_VERIFICATION_SCREEN)
            }
        }
    }

}