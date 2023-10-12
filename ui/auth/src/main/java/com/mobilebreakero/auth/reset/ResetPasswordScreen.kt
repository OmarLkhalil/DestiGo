package com.mobilebreakero.auth.reset

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.auth.components.AuthContent
import com.mobilebreakero.auth.components.AuthTextField
import com.mobilebreakero.auth.components.ForgetPassword

@Composable
fun ResetPasswordScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthContent("Reset Password")
            Text(
                text = "Type your email to send\n" +
                        "a confirmation code for reset password",
                color = Color(0xffB3B3B3),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            var emailText by remember { mutableStateOf("") }
            AuthTextField (
                text = emailText,
                onValueChange = { emailText = it },
                label = "Email"
            )
            Spacer(modifier = Modifier.height(30.dp))
            ForgetPassword(email = emailText, navController = navController)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "return to login",
                color = Color(0xffB3B3B3),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 220.dp)
                    .clickable {
                        navController.navigate(route = "LoginScreen")
                    }
            )
        }
    }
}