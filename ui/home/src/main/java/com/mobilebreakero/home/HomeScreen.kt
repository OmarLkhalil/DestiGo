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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.home.components.AddButtonDesign
import com.mobilebreakero.home.components.ForYouItem
import com.mobilebreakero.home.components.PostItem
import com.mobilebreakero.home.components.TitleText
import com.mobilebreakero.home.components.TopScreenImage
import com.mobilebreakero.home.viewmodel.HomeViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser
    val posts by viewModel.postsFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPosts()
    }

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
                when (posts) {
                    is Response.Loading -> {
                        // Handle loading state if needed
                    }
                    is Response.Success -> {
                        val posts = (posts as Response.Success<List<Post>>).data
                        items(posts.size) { index ->
                            PostItem(
                                name = posts[index].userName!!,
                                numberOfLike = posts[index].numberOfLikes.toString(),
                                location = posts[index].location!!,
                                imageUri = posts[index].image!!
                            )
                        }
                    }
                    is Response.Failure -> {
                        val error = (posts as Response.Failure).e
                    }
                }
            }
        }
    }
    AddButtonDesign(navController = navController)
}