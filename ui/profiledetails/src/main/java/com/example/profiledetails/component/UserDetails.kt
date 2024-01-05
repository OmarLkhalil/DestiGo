package com.example.profiledetails.component

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.home.components.ProfileImage

@Composable
fun UserDetails(user: MutableState<AppUser>) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        ProfileImage(
            data = Uri.parse(user.value.photoUrl),
            contentDescription = "profile photo",
            modifier = Modifier
                .size(80.dp)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = user.value.name!!,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4F80FF)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = user.value.status!!,
            fontSize = 22.sp
        )
    }
}