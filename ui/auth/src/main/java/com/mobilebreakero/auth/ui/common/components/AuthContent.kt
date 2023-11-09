package com.mobilebreakero.auth.ui.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilebreakero.auth.R


@Composable
fun AuthContent(text: String) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "This is logo icon in auth screen"
    )
    Spacer(modifier = Modifier.height(30.dp))
    Text(
        text = text,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(30.dp))
}