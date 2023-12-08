package com.mobilebreakero.profile.yourposts


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.util.Response


@Composable
fun YourPostsScreenContent(
    viewModel: YourPostsViewModel = hiltViewModel()
) {

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

    val postsResults = viewModel.postsResult

    viewModel.getPosts(userId = user.value.id ?: "")

    if (postsResults.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You have no posts",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    } else
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (posts) {

                is Response.Loading -> {

                }

                is Response.Success -> {

                    val posts = (posts as Response.Success<List<Post>>).data

                    items(posts.size) { index ->


                        var postsLikes = posts[index].numberOfLikes
                        val context = LocalContext.current
                        val isLiked =
                            remember { mutableStateOf(posts[index].likedUserIds.contains(user.value.id)) }

                        PostItem(
                            name = posts[index].userName!!,
                            numberOfLike = postsLikes,
                            location = posts[index].location!!,
                            imageUri = posts[index].image!!,
                            profilePhoto = posts[index].profilePhoto!!,
                            onClick = {

                                val postId = posts[index].id!!

                                viewModel.likePost(
                                    postId = postId,
                                    likes = postsLikes,
                                    userId = user.value.id!!,
                                    context = context
                                )

                            },
                            isLiked.value
                        )
                    }
                }

                else -> {}
            }

        }
}