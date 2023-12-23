package com.mobilebreakero.home

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.home.components.ProfileImage
import com.mobilebreakero.viewModel.HomeViewModel


@Composable
fun PostDetailsScreen(
    postId: String,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {

    val details by viewModel.detailsResult.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPostDetails(postId)
    }

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    when (details) {
        is Response.Success -> {
            val postDetails = (details as Response.Success<Post>).data
            val profilePhoto by remember { mutableStateOf(postDetails.profilePhoto) }
            val name by remember { mutableStateOf(postDetails.userName) }
            val location by remember { mutableStateOf(postDetails.location) }
            val imageUri by remember { mutableStateOf(postDetails.image) }
            val postStatus by remember { mutableStateOf(postDetails.text) }
            val context = LocalContext.current
            val likeCount = remember { mutableIntStateOf(postDetails.numberOfLikes) }
            val isLikeBy =
                remember { mutableStateOf(postDetails.likedUserIds.contains(user.value.id)) }

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxSize()
                    .border(
                        width = .4.dp,
                        color = Color(0xFF4F80FF),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(Color(0xFFF8FAFF))
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    ProfileImage(
                        contentDescription = "Profile Photo",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        data = Uri.parse(profilePhoto)
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        name?.let {
                            Text(
                                text = it,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4F80FF)
                            )
                        }
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.location),
                                contentDescription = "Location Icon",
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF4F80FF)
                            )
                            location?.let {
                                Text(
                                    text = it
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                postStatus?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4F80FF)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                SubcomposeAsyncImage(
                    model = Uri.parse(imageUri),
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(3.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 12.dp,
                                bottomStart = 12.dp,
                                topStart = 5.dp,
                                topEnd = 5.dp
                            )
                        ),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 20.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PostContent(
                            icon = if (isLikeBy.value) R.drawable.likefilled else R.drawable.like,
                            description = "Like Icon",
                            text = "${likeCount.intValue}",
                        ) {
                            viewModel.likePost(
                                postId,
                                userId = user.value.id!!,
                                likes = likeCount.intValue,
                                context = context
                            )
                        }
                        Spacer(modifier = Modifier.width(90.dp))
                        PostContent(
                            icon = R.drawable.comment,
                            description = "Comment Icon",
                            text = "comment"
                        ) {
                            navController.navigate("comment/${postDetails.id}")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        PostContent(
                            icon = R.drawable.share,
                            description = "Share Icon",
                            text = "share"
                        ) {
                            viewModel.sharePost(
                                postId = postDetails.id!!,
                                userId = postDetails.userId!!,
                                userName = postDetails.userName!!,
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
                        }
                    }
                }

                if (postDetails.comments != null) {
                    Box(
                        modifier = Modifier
                            .height(.5.dp)
                            .fillMaxWidth()
                            .background(Color(0xFF4F80FF))
                    )
                    Text(
                        text = "Comments",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4F80FF)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    postDetails.comments?.let {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .align(Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(it.size) { comment ->
                                CommentItem(
                                    commenter = postDetails.comments!![comment].userName!!,
                                    comment = postDetails.comments!![comment].text!!
                                )
                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        is Response.Failure -> {
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
        }

        else -> {
            Response.Loading
        }
    }


}

@Composable
fun CommentItem(commenter: String, comment: String) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .size(width = 350.dp, height = 100.dp)
            .background(Color(0xFFD5E1FF).copy(alpha = 0.5f))
            .border(
                width = .2.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Text(
            text = commenter,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            fontSize = 24.sp,
            color = Color(0xFF4F80FF)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = comment, modifier = Modifier.padding(8.dp), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun PostContent(icon: Int, description: String, text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = description,
                modifier = Modifier
                    .size(20.dp),
                tint = Color(0xFF4F80FF)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, color = Color.Gray, fontSize = 8.sp)
        }
    }
}