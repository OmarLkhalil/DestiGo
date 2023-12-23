package com.mobilebreakero.search.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
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
            .width(360.dp)
            .wrapContentHeight()
            .padding(20.dp, 12.dp, 20.dp, 12.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(22.dp),
                clip = true
            ),
        shape = RoundedCornerShape(22.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFEFEEEE),
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        label = { Text(text = label) },
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon",
                tint = Color.Black
            )
        }
    )

}
