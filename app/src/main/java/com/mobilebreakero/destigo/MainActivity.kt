package com.mobilebreakero.destigo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mobilebreakero.common_ui.viewmodels.AuthViewModel
import com.mobilebreakero.destigo.ui.theme.DestiGoTheme
import com.mobilebreakero.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel by viewModels<AuthViewModel>()

    private val FIRST_LAUNCH_PREFS = "FirstLaunchPrefs"
    private val FIRST_LAUNCH_KEY = "FirstLaunch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences(FIRST_LAUNCH_PREFS, Context.MODE_PRIVATE)
        val isFirstLaunch = prefs.getBoolean(FIRST_LAUNCH_KEY, true)

        setContent {
            DestiGoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavHost(isFirstLaunch, authViewModel)
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
