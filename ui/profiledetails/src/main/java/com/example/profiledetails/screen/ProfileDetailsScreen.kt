package com.example.profiledetails.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.profiledetails.component.PostsLabel
import com.example.profiledetails.component.UserDetails
import com.example.profiledetails.viewmodel.PostsViewModel
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.domain.model.AppUser

@Composable
fun ProfileDetailsScreen(
    userID: String?,
    navController: NavController,
    viewModel: PostsViewModel = hiltViewModel()
) {

    val user = remember { mutableStateOf(AppUser()) }

    val posts by viewModel.postsFlow.collectAsState()

    val postsResults = viewModel.postsResult

    viewModel.getPosts(userId = user.value.id ?: "")

    GetUserFromFireStore(
        id = userID,
        user = { userId ->
            userId.id = userID
            user.value = userId
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UserDetails(user = user)
            Spacer(modifier = Modifier.height(12.dp))
            PostsLabel(
                user = user,
                viewModel = viewModel,
                posts = posts,
                postsResults = postsResults,
                navController = navController
            )
        }
    }
}