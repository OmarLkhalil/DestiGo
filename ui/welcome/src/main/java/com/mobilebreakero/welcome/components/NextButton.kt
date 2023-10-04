package com.mobilebreakero.welcome.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobilebreakero.welcome.R


@Composable
fun NextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        containerColor = Color(0xFF4F80FF),
        modifier = modifier
            .height(55.dp)
            .width(190.dp)

    ) {
        Text(text = text, color = Color.White)
        Icon(painter = painterResource(id = R.drawable.navigate_next), "", tint = Color.White)
    }
}