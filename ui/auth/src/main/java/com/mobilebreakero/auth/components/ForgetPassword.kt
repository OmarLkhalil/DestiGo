package com.mobilebreakero.auth.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilebreakero.domain.util.sendEmail

@Composable
fun ForgetPassword(email: String, navController: NavController) {

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.clickable {
            showDialog = true
        },
    ) {
        AuthButton(
            onClick = { showDialog = true },
            text = "Send Confirmation Code",
            modifier = Modifier
                .width(290.dp)
                .height(45.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(horizontal = 20.dp, vertical = 2.dp),
            border = BorderStroke(1.dp, Color(0xff4F80FF)),
            buttonColor = Color(0xff4F80FF)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            text = {
                Text(text = "Send Email on This Email?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        sendEmail(email, context = context)
                        navController.navigate("LoginScreen")
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xff4F80FF)),
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                        navController.navigate("LoginScreen")
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xff4F80FF)),
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}