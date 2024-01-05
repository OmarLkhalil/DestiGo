package com.example.profiledetails.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.profiledetails.viewmodel.PostsViewModel
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.repo.postResponse
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.home.components.PostItem

@Composable
fun PostsLabel(
    user: MutableState<AppUser>,
    viewModel: PostsViewModel,
    posts: postResponse,
    postsResults: List<Post>,
    navController: NavController
) {
    Column {
        Text(
            text = "   ${user.value.name!!} posts",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        if (postsResults.isEmpty()) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = "Loading.....")
        } else {
            LazyColumn(
                modifier = Modifier
                    .height(450.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (posts) {

                    is Response.Loading -> {

                    }

                    is Response.Success -> {

                        val posts = (posts).data

                        items(posts.size) { index ->
                            val context = LocalContext.current

                            var postsLikes by remember { mutableIntStateOf(posts[index].numberOfLikes) }

                            var isLiked by
                            remember { mutableStateOf(posts[index].likedUserIds.contains(user.value.id)) }

                            val userProfile = posts[index].userId

                            LaunchedEffect(Unit) {
                                postsLikes = posts[index].numberOfLikes
                            }
                            PostItem(
                                name = posts[index].userName!!,
                                numberOfLike = postsLikes,
                                location = posts[index].location!!,
                                imageUri = posts[index].image!!,
                                profilePhoto = posts[index].profilePhoto!!,
                                text = posts[index].text ?: "",
                                onLikeClick = {
                                    isLiked = !isLiked
                                    if (isLiked) {
                                        postsLikes += 1
                                    } else if (postsLikes > 0) {
                                        postsLikes -= 1
                                    }
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
                                onProfileClick = {},
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
}