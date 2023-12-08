package com.mobilebreakero.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.home.components.AddButtonDesign
import com.mobilebreakero.home.components.ForYouItem
import com.mobilebreakero.home.components.PostItem
import com.mobilebreakero.home.components.TitleText
import com.mobilebreakero.home.components.TopScreenImage
import com.mobilebreakero.viewModel.HomeViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    val posts by viewModel.postsFlow.collectAsState()

    val trips by viewModel.tripsFlow.collectAsState()

    LaunchedEffect(posts) {
        viewModel.getPosts()
    }

    LaunchedEffect(trips) {
        viewModel.getTrips(id = user.value.id ?: "")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            (if (user.value.id != null) user.value.name else "user")?.let {
                TopScreenImage(
                    user = it,
                    navController = navController
                )
            }

            TitleText(text = "For You")

            when (trips) {
                is Response.Loading -> {

                }

                is Response.Success -> {
                    val tripsResult = (trips as Response.Success<List<Trip>>).data
                    Log.e("HomeScreen", "HomeScreen: $tripsResult")

                    LazyRow {
                        items(tripsResult.size) { index ->
                            ForYouItem(
                                title = tripsResult[index].name!!,
                                desc = tripsResult[index].location!!,
                                image = tripsResult[index].image!!,
                            )
                        }
                    }
                }

                else -> {}
            }


            TitleText(text = "Travellers Posts")
            LazyColumn(
                modifier = Modifier
                    .height(430.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (posts) {

                    is Response.Loading -> {

                    }

                    is Response.Success -> {

                        val posts = (posts as Response.Success<List<Post>>).data

                        items(posts.size) { index ->
                            val context = LocalContext.current

                            val postsLikes = posts[index].numberOfLikes

                            val isLiked =
                                posts[index].likedUserIds.contains(user.value.id)

                            val userProfile = posts[index].userId

                            PostItem(
                                name = posts[index].userName!!,
                                numberOfLike = postsLikes,
                                location = posts[index].location!!,
                                imageUri = posts[index].image!!,
                                profilePhoto = posts[index].profilePhoto!!,
                                text = posts[index].text ?: "",
                                onLikeClick = {
                                    viewModel.likePost(
                                        postId = posts[index].id!!,
                                        userId = user.value.id!!,
                                        context = context,
                                        likes = postsLikes,
                                    )
                                },
                                onCommentClick = {
                                    navController.navigate("comment/${posts[index].id}")
                                },
                                onPostClick = {
                                    navController.navigate("postDetails/${posts[index].id}")
                                },
                                onProfileClick = {
                                    navController.navigate("profileDetails/${userProfile}")
                                },
                                onShareClick = {

                                    viewModel.sharePost(
                                        postId = posts[index].id!!,
                                        userId = user.value.id!!,
                                        userName = user.value.name!!,
                                    )

                                    when (viewModel.sharePostResponse) {

                                        is Response.Success -> {
                                            Toast.makeText(
                                                context,
                                                "Post shared successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        is Response.Failure -> {
                                            Toast.makeText(
                                                context,
                                                "Error sharing post",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        else -> {
                                        }
                                    }


                                },
                                isLiked = isLiked
                            )
                        }
                    }

                    else -> {}
                }

            }
        }
    }

    AddButtonDesign(navController = navController)
}