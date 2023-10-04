package com.example.auth.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.auth.R
import com.example.auth.content.AuthButton

@Composable
fun StartAuthScreen(navController: NavController) {
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.mainauthimage),
                contentDescription = "This is main image in auth screen",
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "Planning, Sharing, Enjoying Life",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "document your travel experiences in a digital journal. \n" +
                        "Upload photos, write entries,\n" +
                        "and relive your favorite moments. \n" +
                        "Share your adventures with a vibrant\n" +
                        "community of fellow travelersaring, Enjoying Life",
                color = Color(0xffB3B3B3),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 12.dp)
            )
            AuthButton(
                onClick = { navController.navigate(route = "LoginScreen") },
                text = "Login",
                textColor = Color.Black
            )
            AuthButton(
                onClick = { navController.navigate(route = "SignUpScreen") },
                buttonColor = Color(0xff4F80FF),
                text = "Sign Up"
            )
        }
    }
}