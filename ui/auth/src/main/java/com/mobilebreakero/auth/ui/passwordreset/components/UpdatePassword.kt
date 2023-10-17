package com.mobilebreakero.auth.ui.passwordreset.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.auth.ui.passwordreset.PasswordResetViewModel
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CONFIRM_CODE_SENT
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.PASSWORD_UPDATED_SUCCESSFULLY
import com.mobilebreakero.domain.util.Response.Loading
import com.mobilebreakero.domain.util.Response.Success
import com.mobilebreakero.domain.util.Response.Failure
import com.mobilebreakero.domain.util.Utils.Companion.print
import com.mobilebreakero.domain.util.Utils.Companion.showMessage

@Composable
fun UpdatePassword(
    viewModel: PasswordResetViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    when(val updatePassword = viewModel.updatePasswordResponse) {
        is Loading -> LoadingIndicator()
        is Success -> {
            val isPasswordUpdated = updatePassword.data
            LaunchedEffect(isPasswordUpdated) {
                if (isPasswordUpdated) {
                    showMessage(context, "Updated Successfully")
                    navController.navigate(PASSWORD_UPDATED_SUCCESSFULLY)
                }
            }
        }
        is Failure -> updatePassword.apply {
            LaunchedEffect(e) {
                print(e)
            }
        }

        else -> {

        }
    }
}