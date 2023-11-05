package com.mobilebreakero.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardsDisplay(
    onClick: () -> Unit
) {

    val items = listOf(
        ListItem(Color(0xFFD5E1FF), "Your Posts"),
        ListItem(Color(0xFFEDFDF6), "Your Trips"),
        ListItem(Color(0xFFEBEBEB), "Account"),
        ListItem(Color(0xFFF9FDED), "Saved"),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(items = items, itemContent = { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(1f)
                    .padding(15.dp)
                    .shadow(
                        5.dp,
                        shape = RoundedCornerShape(22.dp)
                    )
                    .clickable { onClick() }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(item.color)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(
                        item.textString,
                        fontSize = 24.sp, fontWeight = FontWeight.Bold
                    )
                }
            }
        })
    }
}

data class ListItem(
    val color: Color,
    val textString: String
)