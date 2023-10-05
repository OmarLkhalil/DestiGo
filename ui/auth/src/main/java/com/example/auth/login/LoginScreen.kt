package com.example.auth.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.util.Resource
import com.example.auth.components.AuthButton
import com.mobilebreakero.auth.components.AuthContent
import com.example.auth.components.AuthTextField
import com.mobilebreakero.auth.components.ShowToast
import com.mobilebreakero.common_ui.viewmodels.AuthViewModel
import com.mobilebreakero.domain.util.sendMessage

@Composable
fun LoginScreen (
    viewModel: AuthViewModel?,
    navController: NavController
) {

    val authResource = viewModel?.loginFlow?.collectAsState()
    val context = LocalContext.current

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
            Spacer(modifier = Modifier.height(20.dp))
            ForgetPassword(emailText, context)
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
                        Log.e("Login", it.exception.message.toString())
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

@Composable
fun ForgetPassword(email: String, context: Context) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.clickable {
            showDialog = true
        },
    ) {
        Text(
            text = "Forget Password?",
            color = Color(0xffB3B3B3),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 220.dp)
                .clickable {
                    showDialog = true
                }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            text = {
                Text(text = "Forget Password \n" +
                        "Send code to your email address")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        //sendEmail(email)
                        sendMessage(context = context)
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xff4F80FF)),
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xff4F80FF)),
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}