package com.mobilebreakero.profile.account.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.profile.R
import com.mobilebreakero.profile.component.ProfileImage

@Composable
fun ProfileSettingsContent(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .height(500.dp)
                .width(390.dp)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, Color(0xFF4F80FF), shape = RoundedCornerShape(20.dp))
                .align(Alignment.Center)
                .background(Color(0xFFF8FAFF)),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Omar Khalil",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4F80FF)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Cairo, Egypt",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Plan, and manage your details more easily with an account",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
                    .align(CenterHorizontally),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.photo, text = "change photo", onClick = {})
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.info, text = "update your status", onClick = {})
            Spacer(modifier = Modifier.height(10.dp))
            SettingsCard(icon = R.drawable.location, text = "update your location", onClick = {})
            Spacer(modifier = Modifier.height(10.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(bottom = 5.dp, top = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProfileImage(
                data = R.drawable.culture, contentDescription = "", modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}
