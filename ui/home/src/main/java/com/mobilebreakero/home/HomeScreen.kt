package com.mobilebreakero.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.domain.util.DataUtils
import com.mobilebreakero.home.components.AddButtonDesign
import com.mobilebreakero.home.components.ForYouItem
import com.mobilebreakero.home.components.PostItem
import com.mobilebreakero.home.components.TitleText
import com.mobilebreakero.home.components.TopBarScreen

@Composable
fun HomeScreen(navController: NavController) {

    val user = DataUtils.user
    val firebaseUser = Firebase.auth.currentUser

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TopBarScreen(user = user?.name ?: firebaseUser?.displayName.toString())
            TitleText(text = "For You")
            LazyRow {
                items(10) {
                    ForYouItem()
                }
            }
            TitleText(text = "Travellers Posts")
            LazyColumn (
                modifier = Modifier.height(2000.dp)
            ) {
                items(10) {
                    PostItem("500")
                }
            }

        }
    }
    AddButtonDesign(navController = navController)
}