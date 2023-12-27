package com.mobilebreakero.interestedplaces.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun InterestsSection(
    contentDescription: String,
    modifier: Modifier = Modifier,
    item: InterestsItem
) {

    val isItemSelected = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = modifier
            .width(146.dp)
            .height(125.dp)
            .padding(6.dp, 12.dp),
        shape = RoundedCornerShape(15.dp),
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (selectedItemsList.size < 3) {
                        selectedItemsList.add(item)
                        isItemSelected.value = !isItemSelected.value
                    } else {
                        selectedItemsList.removeLast()
                        isItemSelected.value = false
                        Toast
                            .makeText(
                                context,
                                "You can only choose 3 interests",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
        ) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = contentDescription,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(item.title, style = TextStyle(color = Color.White, fontSize = 20.sp))
            }

            val imageRes = if (isItemSelected.value) {
                Icons.Filled.CheckCircle
            } else {
                Icons.Outlined.CheckCircle
            }

            Icon(
                imageVector = imageRes,
                contentDescription = "choose",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(30.dp),
                tint = Color.White
            )
        }
    }
}