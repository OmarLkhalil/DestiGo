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
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mobilebreakero.common_ui.viewmodels.AuthViewModel
import com.mobilebreakero.destigo.components.BottomNavigation
import com.mobilebreakero.destigo.ui.theme.DestiGoTheme
import com.mobilebreakero.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel by viewModels<AuthViewModel>()

    private val FIRST_LAUNCH_PREFS = "FirstLaunchPrefs"
    private val FIRST_LAUNCH_KEY = "FirstLaunch"

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
                            viewModel = authViewModel,
                            navController
                        )
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
}