package com.example.auth.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


@Composable
fun ShowToast(
    message: String,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(message)
    }
}