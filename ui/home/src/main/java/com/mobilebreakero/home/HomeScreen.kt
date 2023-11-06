package com.mobilebreakero.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.util.DataUtils
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.home.components.AddButtonDesign
import com.mobilebreakero.home.components.ForYouItem
import com.mobilebreakero.home.components.PostItem
import com.mobilebreakero.home.components.TitleText
import com.mobilebreakero.home.components.TopScreenImage
import com.mobilebreakero.home.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var user by remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    LaunchedEffect(viewModel) {
        viewModel.getUser(firebaseUser?.uid ?: firebaseUser?.uid ?: "").collect { userResponse ->
            when (userResponse) {
                is Response.Success -> {
                    val userData = (userResponse).data
                    DataUtils.user = userData
                    user = userData
                    Log.e("A7A", userData.name!! + "LOL12")
                }
                is Response.Failure -> {
                    val exception = (userResponse).e
                    // Handle the failure state, e.g., show an error message
                    Log.e("A7A", exception.message.toString())
                }
                Response.Loading -> {
                    // Handle the loading state if needed
                }
                else -> {}
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TopScreenImage(
                user = user.name!!,
                navController = navController
            )
            TitleText(text = "For You")
            LazyRow{
                items(5) {
                    ForYouItem()
                }
            }
            TitleText(text = "Travellers Posts")
            LazyColumn(
                modifier = Modifier.height(2000.dp)
            ) {
                items(2) {
                    PostItem("500")
                }
            }
        }
    }
    AddButtonDesign(navController = navController)
}