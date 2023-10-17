package com.mobilebreakero.interestedplaces.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilebreakero.interestedplaces.R

@Composable
fun InterestsSection(
    painter: Int,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isChoosed: Boolean = false
) {

    val isSelected =  remember { mutableStateOf(isChoosed) }

    Card(
        modifier = modifier
            .width(146.dp)
            .height(125.dp)
            .padding(6.dp, 12.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick()
            }) {
            Image(
                painter = painterResource(id = painter), contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(title, style = TextStyle(color = Color.White, fontSize = 20.sp))
            }

            val imageRes = if (isSelected.value) {
                painterResource(id = R.drawable.filledchoosed)
            } else {
                painterResource(id = R.drawable.outlinedchoose)
            }

            Image(
                painter = imageRes, contentDescription = "choose", modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(30.dp)
            )
        }
    }
}