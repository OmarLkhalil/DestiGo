package com.mobilebreakero.home.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.mobilebreakero.home.R


@Composable
fun PostItem(
    name: String,
    numberOfLike: Int,
    location: String,
    imageUri: String,
    text: String,
    profilePhoto: String,
    onLikeClick: () -> Unit,
    onPostClick: () -> Unit,
    isLiked: Boolean,
    onCommentClick: () -> Unit,
    onProfileClick: () -> Unit,
    onShareClick: () -> Unit,
) {

    val likeCount = remember { mutableStateOf(numberOfLike) }
    val isLikeBy = remember { mutableStateOf(isLiked) }

    LaunchedEffect(likeCount, isLikeBy) {
        likeCount.value = numberOfLike
        isLikeBy.value = isLiked
    }

    Box(
        modifier = Modifier
            .width(350.dp)
            .wrapContentHeight()
            .padding(10.dp)
            .background(Color.Transparent)
    ) {

        Column(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = true,
                    ambientColor = Color(0xFFD5E1FF)
                )
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .align(Alignment.Center)
                .background(Color(0xFFD5E1FF))
                .clickable { onPostClick() }
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                ProfileImage(
                    contentDescription = "Profile Photo",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    data = Uri.parse(profilePhoto)
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = name,
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
                            text = location
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4F80FF)
            )
            Spacer(modifier = Modifier.height(8.dp))
            SubcomposeAsyncImage(
                model = Uri.parse(imageUri),
                contentDescription = "Post Image",
                modifier = Modifier
                    .width(320.dp)
                    .height(280.dp)
                    .padding(3.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 12.dp,
                            bottomStart = 12.dp,
                            topStart = 5.dp,
                            topEnd = 5.dp
                        )
                    ),
                contentScale = ContentScale.FillBounds
            )

            Box {
                Row(
                    modifier = Modifier.padding(start = 20.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PostContent(
                        icon = if (isLikeBy.value) R.drawable.likefilled else R.drawable.like,
                        description = "Like Icon",
                        text = "${likeCount.value}",
                    ) {
                        onLikeClick()
                    }
                    Spacer(modifier = Modifier.width(90.dp))
                    PostContent(
                        icon = R.drawable.comment,
                        description = "Comment Icon",
                        text = "comment"
                    ) {
                        onCommentClick()
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    PostContent(
                        icon = R.drawable.share,
                        description = "Share Icon",
                        text = "share"
                    ) {
                        onShareClick()
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
fun PostContent(icon: Int, description: String, text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
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