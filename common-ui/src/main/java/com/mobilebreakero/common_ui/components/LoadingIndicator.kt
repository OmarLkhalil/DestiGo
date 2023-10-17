package com.mobilebreakero.common_ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(){
    Row {
        Text("Loading...")
        Spacer(modifier = Modifier.width(3.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(40.dp),
            color = Color(0xFF6200EE)
        )
    }

}