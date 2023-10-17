package com.mobilebreakero.interestedplaces.components

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mobilebreakero.interestedplaces.R

@Composable
fun VerticalGrid() {

    val interests = listOf(
        InterestsItem(R.drawable.mountain, "Mountain"),
        InterestsItem(R.drawable.sea, "Sea"),
        InterestsItem(R.drawable.resturant, "Restaurant"),
        InterestsItem(R.drawable.hotel, "Hotel"),
        InterestsItem(R.drawable.culture, "Culture"),
        InterestsItem(R.drawable.mountain2, "Mountain"),
        InterestsItem(R.drawable.adventure, "Adventure"),
        InterestsItem(R.drawable.shopping, "Shopping"),
    )

    val selectedItems = remember { mutableStateListOf<InterestsItem>() }
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(
            items = interests,
            itemContent = { index, interest ->
                InterestsSection(
                    painter = interest.icon,
                    contentDescription = interest.title,
                    title = interest.title,
                    modifier = Modifier.padding(3.dp),
                    onClick = {
                        if (selectedItems.size <= 2) {
                            interests[index].isSelected = !interests[index].isSelected
                            selectedItems.add(interest)
                        }
                        else {
                            interests[index].isSelected = false
                            Toast.makeText(
                                context,
                                "You can only choose 3 interests",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }, isChoosed = interest.isSelected
                )
            }
        )
    }
}


data class InterestsItem(val icon: Int, val title: String, var isSelected: Boolean = false)
