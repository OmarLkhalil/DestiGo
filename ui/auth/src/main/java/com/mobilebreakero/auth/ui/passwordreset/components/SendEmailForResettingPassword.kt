package com.mobilebreakero.auth.ui.passwordreset.components


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.auth.ui.passwordreset.PasswordResetViewModel
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CONFIRM_CODE_SENT
import com.mobilebreakero.domain.util.Response.Loading
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.Utils.Companion.print
import com.mobilebreakero.domain.util.Utils.Companion.showMessage

@Composable
fun SendEmailForResettingPassword(
    viewModel: PasswordResetViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    when(val sendEmailVerificationResponse = viewModel.sendPasswordResetEmailResponse) {
        is Loading -> LoadingIndicator()
        is Success -> {
            val isEmailSent = sendEmailVerificationResponse.data
            LaunchedEffect(isEmailSent) {
                if (isEmailSent) {
                    showMessage(context, "Email sent")
                    navController.navigate(CONFIRM_CODE_SENT)
                }
            }
        }
        is Failure -> sendEmailVerificationResponse.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }

        else -> {

        }
    }
}