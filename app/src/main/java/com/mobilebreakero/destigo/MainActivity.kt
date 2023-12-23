package com.mobilebreakero.destigo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mobilebreakero.auth.ui.common.components.MainViewModel
import com.mobilebreakero.common_ui.components.DestiGoTopAppBar
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.EMAIL_VERIFICATION_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.START_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.WELCOME_SCREEN
import com.mobilebreakero.destigo.ui.theme.DestiGoTheme
import com.mobilebreakero.home.components.BottomNavigation
import com.mobilebreakero.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val FIRST_LAUNCH_PREFS = "FirstLaunchPrefs"
    private val FIRST_LAUNCH_KEY = "FirstLaunch"
    private val REQUEST_LOCATION_PERMISSION = 129
    private val REQUEST_CAMERA_PERMISSION = 232
    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(FIRST_LAUNCH_PREFS, Context.MODE_PRIVATE)
        val isFirstLaunch = prefs.getBoolean(FIRST_LAUNCH_KEY, true)


        setContent {
            DestiGoTheme {
                val navController = rememberNavController()

                val startDestination = if (isFirstLaunch) {
                    WELCOME_SCREEN
                } else {
                    authState()
                }

                Scaffold(
                    topBar = { DestiGoTopAppBar(navController) },
                    bottomBar = { BottomNavigation(navController) }
                ) { pv ->
                    Box(modifier = Modifier.padding(pv)) {
                        MainNavHost(navController, startDestination)
                    }
                }
            }
        }

        if (isFirstLaunch) {
            val editor = prefs.edit()
            editor.putBoolean(FIRST_LAUNCH_KEY, false)
            editor.apply()
            requestPermission()
        } else {
            checkPermission()
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }

    private fun requestPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    private fun checkPermission(): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @Composable
    private fun authState(): String {
        val isUserSignedOut = viewModel.getAuthState().collectAsState().value
        return if (isUserSignedOut) {
            START_SCREEN
        } else {
            if (viewModel.isEmailVerified) {
                HOME_SCREEN
            } else {
                EMAIL_VERIFICATION_SCREEN
            }
        }
    }
}