package com.mobilebreakero.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilebreakero.details.R


@Composable
fun TripDetailsCard(
    title: String,
    content: @Composable () -> Unit,
    onEditClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(.1.dp, Color(0xff4F80FF), RoundedCornerShape(3.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .wrapContentHeight()
                        .wrapContentWidth()
                        .clickable { onEditClick() }
                        .border(.6.dp, Color(0xff4F80FF), RoundedCornerShape(20.dp))
                ) {
                    Text(
                        text = "Edit trip",
                        color = Color(0xff4F80FF),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(7.dp),
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "edit trip",
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp),
                        tint = Color(0xff4F80FF)
                    )

                }
            }

            content()
        }

    }
}