package com.mobilebreakero.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ItemsChip(title: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(20.dp))
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable { onClick() }
            .border(.6.dp, Color(0xff4F80FF), RoundedCornerShape(20.dp))
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            modifier = Modifier.padding(10.dp),
        )
    }

}
