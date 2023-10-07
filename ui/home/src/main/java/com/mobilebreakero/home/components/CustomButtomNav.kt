package com.mobilebreakero.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButtonForButtonNav(item: Destinations, isSelected: Boolean, onClick: () -> Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.route,
            tint = if (isSelected) Color(0xFF4F80FF) else Color.DarkGray.copy(alpha = 0.5f),
            modifier = Modifier.size(30.dp)
        )
        if (isSelected) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
        Text(
            text = item.route,
            fontSize = 14.sp,
            style = TextStyle(fontWeight = Bold),
            color = if (isSelected) Color(0xFF4F80FF) else Color.DarkGray.copy(alpha = 0.5f),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
