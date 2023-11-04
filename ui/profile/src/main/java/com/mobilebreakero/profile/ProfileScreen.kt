package com.mobilebreakero.profile

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Column {
        ProfileSection()

    }

}


@Composable
fun ProfileSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        {
            RoundImage(
                image = painterResource(id = R.drawable.culterintrest), modifier = Modifier
                    .size(100.dp)
                    .weight(3f)

            )
            Spacer(modifier = Modifier.width(16.dp))
            NameSection(modifier = Modifier.weight(7f))
        }
        Spacer(modifier = Modifier.height(15.dp))
        DescriptionSection()
        Spacer(modifier = Modifier.height(20.dp))
        CardsDisplay()
    }

}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .padding(3.dp)
            .clip(CircleShape)
    )

}

@Composable
fun NameSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        ProfileStat(name = "Omar Khalil", address = "Cairo, Egypt")
    }

}

@Composable
fun ProfileStat(
    name: String,
    address: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 31.sp,
            color = Color(0xFF5483fe)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = address,
            fontSize = 15.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun DescriptionSection(

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
        ) {
            Text(
                text = "Plan, and manage your details more easily with an account",
                fontSize = 27.sp,
                textAlign = TextAlign.Center

            )
        }



        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Your Account",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,


            )
    }
}

@Composable
fun CardsDisplay() {


    val items = listOf(
        ListItem(Color(0xFFD5E1FF), "Your Posts"),
        ListItem(Color(0xFFEDFDF6), "Your Trips"),
        ListItem(Color(0xFFEBEBEB), "Account"),
        ListItem(Color(0xFFF9FDED), "Saved"),
    )

    val selectedItems = remember { mutableStateListOf<Colors>() }
    val context = LocalContext.current
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
                        fontSize = 28.sp, fontWeight = FontWeight.Bold
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

