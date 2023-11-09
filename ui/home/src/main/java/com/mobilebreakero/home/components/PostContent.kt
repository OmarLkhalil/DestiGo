package com.mobilebreakero.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.mobilebreakero.home.R


@Composable
fun PostItem(numberOfLike: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF8FAFF))
    ) {
        Column {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "profile photo",
                    modifier = Modifier
                        .size(40.dp)
                        .shadow(2.dp, shape = CircleShape)
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = "Omar Khalil",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4F80FF)
                    )
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "Location Icon",
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFF4F80FF)
                        )
                        Text(
                            text = "Cairo, Egypt"
                        )
                    }
                }
            }
            Image(
                painter = painterResource(id = R.drawable.social),
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(3.dp)
                    .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
            )
            Box {
                Row(
                    modifier = Modifier.padding(start = 20.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PostContent(
                        icon = R.drawable.like,
                        description = "Like Icon",
                        text = numberOfLike
                    )
                    Spacer(modifier = Modifier.width(90.dp))
                    PostContent(
                        icon = R.drawable.comment,
                        description = "Comment Icon",
                        text = "comment"
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    PostContent(icon = R.drawable.share, description = "Share Icon", text = "share")
                }
            }
        }
    }
}

@Composable
fun PostContent(icon: Int, description: String, text: String) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = description,
                modifier = Modifier
                    .size(20.dp),
                tint = Color(0xFF4F80FF)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, color = Color.Gray, fontSize = 8.sp)
        }
    }
}