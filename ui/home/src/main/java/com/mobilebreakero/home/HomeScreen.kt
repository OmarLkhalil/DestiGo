package com.mobilebreakero.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.home.components.AddButtonDesign
import com.mobilebreakero.home.components.ForYouItem
import com.mobilebreakero.home.components.PostItem
import com.mobilebreakero.home.components.TitleText
import com.mobilebreakero.home.components.TopScreenImage


@Composable
fun HomeScreen(navController: NavController) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopScreenImage(
                user = user.value.name!!,
                navController = navController
            )
            TitleText(text = "For You")
            LazyRow {
                items(5) {
                    ForYouItem()
                }
            }
            TitleText(text = "Travellers Posts")
            LazyColumn(
                modifier = Modifier.height(430.dp)
            ) {
                items(2) {
                    PostItem("500")
                }
            }
        }
    }
    AddButtonDesign(navController = navController)
}