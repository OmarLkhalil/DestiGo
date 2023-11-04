package com.example.addpost

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.addpost.componant.AddPostCard
import com.example.addpost.viewmodel.AddPostViewModel
import com.mobilebreakero.common_ui.components.AppTopBar
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN

@Composable
fun AddPostScreen(
    navController: NavController,
    viewModel: AddPostViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTopBar(navController = navController, route = HOME_SCREEN)
            Spacer(modifier = Modifier.height(50.dp))
            AddPostCard(navController)
        }
    }
}