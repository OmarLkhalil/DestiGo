package com.mobilebreakero.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarScreen(user: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.homeimage),
            contentDescription = "Home top Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
        )

        Text(
            text = "Hello $user",
            color = Color.White,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 85.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 250.dp, start = 35.dp)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Where do you want to go?") },
                modifier = Modifier
                    .width(360.dp)
                    .wrapContentHeight()
                    .padding(20.dp, 12.dp, 20.dp, 12.dp)
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(22.dp),
                        clip = true
                    ),
                shape = RoundedCornerShape(22.dp),
                singleLine = true,
                leadingIcon = {
                    val image = painterResource(R.drawable.ic_search)
                    IconButton(onClick = { }) {
                        Icon(painter = image, "search icon")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ForYouItem() {
    Box(
        modifier = Modifier
            .size(250.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.testimage),
            contentDescription = "test image",
            modifier = Modifier.fillMaxSize()
        )
        Icon(
            Icons.Filled.FavoriteBorder,
            tint = Color.White,
            contentDescription = "Favorite Icon",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(25.dp)
                .size(30.dp)
        )
        Column (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = "Dusit Thani LakeView Cairo",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Cairo, Egypt",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        fontSize = 28.sp,
        modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun PostItem(numberOfLike: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
    ) {
        Column {
            Row (
                modifier = Modifier.padding(8.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "profile photo",
                    modifier = Modifier
                        .size(40.dp)
                        .shadow(2.dp, shape = CircleShape)
                )
                Column (
                    modifier = Modifier.padding(start = 8.dp)
                ){
                    Text(
                        text = "Moaz Mahdy",
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
                    .height(280.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
            )
            Box {
                Row (
                    modifier = Modifier.padding(start = 20.dp, bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PostContent(icon = R.drawable.like, description = "Like Icon", text = numberOfLike)
                    Spacer(modifier = Modifier.width(140.dp))
                    PostContent(icon = R.drawable.comment, description = "Comment Icon", text = "comment")
                    Spacer(modifier = Modifier.width(10.dp))
                    PostContent(icon = R.drawable.share, description = "Share Icon", text = "share")
                }
            }
        }
    }
}

@Composable
fun PostContent(icon: Int,
                description: String,
                text: String
) {
    Box (
        modifier = Modifier
            .border(
            width = 1.dp,
            color = Color(0xFF4F80FF),
            shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row (
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

@Composable
fun AddButtonDesign(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
        ) {
            AddButton(navController, modifier = Modifier.align(Alignment.End))
        }
    }
}

@Composable
fun AddButton(navController: NavController, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(12.dp)
    ) {
        FloatingActionButton(
            onClick = { navController.navigate("AddPost") },
            shape = CircleShape,
            containerColor = Color(0xff4F80FF),
            contentColor = Color.White,
            modifier = modifier
        ) {
            Icon(Icons.Filled.Add, "Add button")
        }
    }
}