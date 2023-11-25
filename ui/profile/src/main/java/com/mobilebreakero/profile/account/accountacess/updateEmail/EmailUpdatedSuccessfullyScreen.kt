package com.mobilebreakero.profile.account.accountacess.updateEmail

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mobilebreakero.profile.account.accountacess.components.PasswordUpdatedSuccessfullyScreenContent

@Composable
fun EmailUpdateSentSuccessfully(navController: NavController) {

    PasswordUpdatedSuccessfullyScreenContent(
        navController = navController,
        passwordOrEmail = "An Email with was sent to your email, check it for updating your email."
    )

}