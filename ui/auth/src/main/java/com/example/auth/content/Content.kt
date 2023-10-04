package com.example.auth.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.R

@Composable
fun AuthButton(
    onClick: ()-> Unit,
    buttonColor: Color? = Color.White,
    text: String,
    textColor: Color? = Color.White
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .shadow(elevation = 0.dp, shape = CircleShape)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp, vertical = 2.dp),
        border = BorderStroke(2.dp, Color(0xff4F80FF)),
        colors = ButtonDefaults.buttonColors(buttonColor!!)
    ) {
        Text(text = text, fontSize = 16.sp, color = textColor!!)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    var textFieldText by remember { mutableStateOf(text) }

    TextField(
        value = textFieldText,
        onValueChange = {
            textFieldText = it
            onValueChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(18.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        label = { Text(text = label) }
    )
}

@Composable
fun AuthContent(text: String) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "This is logo icon in auth screen"
    )
    Spacer(modifier = Modifier.height(30.dp))
    Text(
        text = text,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun ShowToast(
    message: String,
    snackbarHostState: SnackbarHostState
) {
    LaunchedEffect(message) {
        snackbarHostState.showSnackbar(message)
    }
}