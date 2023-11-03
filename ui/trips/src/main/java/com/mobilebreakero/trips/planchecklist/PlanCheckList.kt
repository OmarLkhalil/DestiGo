package com.mobilebreakero.trips.planchecklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.navigation.NavigationRoutes
import com.mobilebreakero.trips.commaon.CreateTripButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanCheckListScreen(
    navController: NavController
){
    var myPassport by remember { mutableStateOf("") }
    var myLaptop by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(title = {
            Text(
                text = "Check List", fontSize = 25.sp, fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.navigate(NavigationRoutes.CREATE_TRIP)
            }) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF4F80FF)), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        //painter = painterResource(id = R.drawable.back),
                        Icons.Filled.ArrowBack,
                        contentDescription = "back to home",
                        modifier = Modifier.size(25.dp),
                        tint = Color.White

                    )
                }
            }
        }, modifier = Modifier.shadow(12.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Check List",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)
        )
        TextField(value = myPassport,
            onValueChange = {
                myPassport = it
            },
            placeholder = { Text(text = "My Passport") },
            modifier = Modifier
                .align(Alignment.Start)
                .width(420.dp)
                .wrapContentHeight()
                .padding(12.dp, 8.dp, 12.dp, 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFEFEEEE),
            ),
            maxLines = 2
        )
        TextField(value = myLaptop,
            onValueChange = {
                myLaptop = it
            },
            placeholder = { Text(text = "My Laptop") },
            modifier = Modifier
                .align(Alignment.Start)
                .width(420.dp)
                .wrapContentHeight()
                .padding(12.dp, 8.dp, 12.dp, 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFEFEEEE),
            ),
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(80.dp))

        CreateTripButton(text = "Add",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .height(50.dp).width(220.dp),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(220.dp))
        CreateTripButton(text = "Next",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .height(50.dp).width(320.dp),
            onClick = {}
        )

    }
}