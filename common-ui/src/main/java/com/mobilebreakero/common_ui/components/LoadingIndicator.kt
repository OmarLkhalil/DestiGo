package com.mobilebreakero.common_ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(){
    Column {
        Text("Loading...")
        CircularProgressIndicator(
            modifier = Modifier.size(30.dp),
            color = Color.Blue
        )
    }
}