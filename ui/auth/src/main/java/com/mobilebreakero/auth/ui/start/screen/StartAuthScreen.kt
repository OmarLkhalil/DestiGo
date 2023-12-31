package com.mobilebreakero.auth.ui.start.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.auth.R
import com.mobilebreakero.common_ui.components.AuthButton
import com.mobilebreakero.auth.ui.start.StartViewModel
import com.mobilebreakero.auth.ui.start.components.SignInAnon
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_IN_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SIGN_UP_SCREEN

@Composable
fun StartAuthScreen(viewModel: StartViewModel = hiltViewModel(), navController: NavController) {


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
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
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
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
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(22.dp))

            AuthButton(
                onClick = { navController.navigate(route = SIGN_IN_SCREEN) },
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
                onClick = { navController.navigate(route = SIGN_UP_SCREEN) },
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
                    viewModel.signInAnnonymously()
                    navController.navigate(route = HOME_SCREEN)
                }
            )
            SignInAnon()
        }
    }
}