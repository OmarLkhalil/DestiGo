package com.mobilebreakero.auth.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.domain.util.Resource
import com.mobilebreakero.auth.components.AuthButton
import com.mobilebreakero.auth.components.AuthContent
import com.mobilebreakero.auth.components.AuthTextField
import com.mobilebreakero.auth.components.ShowToast
import com.mobilebreakero.common_ui.viewmodels.AuthViewModel

@Composable
fun LoginScreen (
    viewModel: AuthViewModel?,
    navController: NavController
) {

    val authResource = viewModel?.loginFlow?.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
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
                text = "Login",
                modifier = Modifier
                    .width(290.dp)
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp, vertical = 2.dp),
                border = BorderStroke(1.dp, Color(0xff4F80FF))
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