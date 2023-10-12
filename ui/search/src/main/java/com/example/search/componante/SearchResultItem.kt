package com.example.search.componante

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchResultItem(text: String) {
    Text (
        text = text,
        modifier = Modifier
            .width(360.dp)
            .wrapContentHeight()
            .padding(20.dp, 12.dp, 20.dp, 12.dp)
    )
}