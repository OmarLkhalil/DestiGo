package com.mobilebreakero.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileStat(
    modifier: Modifier = Modifier,
    name: String? = "",
    address: String,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = name?:"",
            fontWeight = FontWeight.Bold,
            fontSize = 31.sp,
            color = Color(0xFF5483fe)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = address,
            fontSize = 15.sp,
            textAlign = TextAlign.Start
        )
    }
}