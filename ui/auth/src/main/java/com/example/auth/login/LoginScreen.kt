package com.example.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auth.content.AuthButton
import com.example.auth.content.AuthContent
import com.example.auth.content.AuthTextField
import com.example.auth.content.ShowToast
import com.example.data.viewmodels.AuthViewModel
import com.example.domain.util.Resource

@Composable
fun LoginScreen (
    viewModel: AuthViewModel?,
    navController: NavController
) {

    val authResource = viewModel?.loginFlow?.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthContent("Login")
            var emailText by remember { mutableStateOf("") }
            AuthTextField (
                text = emailText,
                onValueChange = { emailText = it },
                label = "Email"
            )
            var passwordText by remember { mutableStateOf("") }
            AuthTextField(
                text = passwordText,
                onValueChange = { passwordText = it },
                label = "Password"
            )
            Spacer(modifier = Modifier.height(20.dp))
            AuthButton(
                onClick = { viewModel?.loginUser(emailText, passwordText) },
                buttonColor = Color(0xff4F80FF),
                text = "Login"
            )
        }
        val snackbarHostState = remember { SnackbarHostState() }
        authResource?.value?.let {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (it) {
                    is Resource.Failure -> {
                        ShowToast(message = it.exception.message.toString(), snackbarHostState)
                    }
                    is Resource.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(16.dp),
                            color = Color.Blue
                        )
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate("HomeScreen") {
                                popUpTo("LoginScreen") { inclusive = true }
                            }
                        }
                    }
                }
            }
        }
    }
}