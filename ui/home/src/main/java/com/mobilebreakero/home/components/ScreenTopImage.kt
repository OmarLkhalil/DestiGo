package com.mobilebreakero.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.SEARCH_SCREEN
import com.mobilebreakero.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScreenImage(user: String, navController: NavController) {

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
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
                .padding(top = 150.dp)
                .clickable { navController.navigate(SEARCH_SCREEN) }
        ) {
            TextField(
                value = "",
                onValueChange = {},
                label = { Text(text = "Where do you want to go?", fontSize = 14.sp) },
                modifier = Modifier
                    .wrapContentWidth()
                    .height(83.dp)
                    .padding(12.dp, 12.dp, 20.dp, 12.dp)
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(22.dp),
                        clip = true
                    ),
                shape = RoundedCornerShape(22.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFFEFEEEE),
                    textColor = Color.Black,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                leadingIcon = {
                    val image = painterResource(R.drawable.ic_search)
                    Icon(painter = image, "search icon")
                }
            )
        }
    }
}