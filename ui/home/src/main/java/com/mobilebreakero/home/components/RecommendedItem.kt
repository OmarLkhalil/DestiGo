package com.mobilebreakero.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mobilebreakero.common_ui.components.CoilImage

@Composable
fun ForYouItem() {

    Box(
        modifier = Modifier
            .size(250.dp)
            .padding(start = 10.dp)
    ) {
        CoilImage(
            contentDescription = "",
            modifier = Modifier
                .height(270.dp)
                .width(200.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.FillBounds,
            data = "https://egypttimetravel.com/wp-content/uploads/2020/06/Cairo.jpg",
            onClick = { },
            title = "Cairo"
        )

        Icon(
            Icons.Filled.FavoriteBorder,
            tint = Color.White,
            contentDescription = "Favorite Icon",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(30.dp)
        )
    }
}