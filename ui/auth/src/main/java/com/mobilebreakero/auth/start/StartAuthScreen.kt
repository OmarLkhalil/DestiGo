package com.mobilebreakero.auth.start

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.auth.R
import com.mobilebreakero.auth.components.AuthButton

@Composable
fun StartAuthScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
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
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(22.dp))

            AuthButton(
                onClick = { navController.navigate(route = "LoginScreen") },
                text = "Login",
                textColor = Color.Black,
                modifier = Modifier
                    .width(290.dp)
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp, vertical = 2.dp),
                border = BorderStroke(1.dp, Color(0xff4F80FF))
            )

            Spacer(modifier = Modifier.height(16.dp))
            AuthButton(
                onClick = { navController.navigate(route = "SignUpScreen") },
                text = "Sign Up",
                buttonColor = Color(0xff4F80FF),
                modifier = Modifier
                    .width(290.dp)
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 20.dp, vertical = 2.dp),
            )
            Spacer(modifier = Modifier.height(33.dp))

            Text(
                text = "Continue as Guest",
                color = Color(0xffB3B3B3),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(route = "HomeScreen")
                }
            )
        }
    }
}