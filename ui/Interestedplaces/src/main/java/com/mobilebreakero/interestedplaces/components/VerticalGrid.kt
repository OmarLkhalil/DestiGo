package com.mobilebreakero.interestedplaces.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilebreakero.interestedplaces.R

val selectedItemsList = mutableListOf<InterestsItem>()
val selectedItemsNames = mutableListOf<String>()

@Composable
fun VerticalGrid() {

    val interests = listOf(
        InterestsItem(R.drawable.mountain, "Mountain"),
        InterestsItem(R.drawable.sea, "Sea"),
        InterestsItem(R.drawable.resturant, "Restaurant"),
        InterestsItem(R.drawable.hotel, "Hotel"),
        InterestsItem(R.drawable.culture, "Culture"),
        InterestsItem(R.drawable.beachh, "Beach"),
        InterestsItem(R.drawable.adventure, "Adventure"),
        InterestsItem(R.drawable.shopping, "Shopping"),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(550.dp)
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
                        if (selectedItemsList.size < 3) {
                            interest.isSelected = !interest.isSelected
                            if (interest.isSelected) {
                                selectedItemsList.add(interest)
                                selectedItemsNames.add(interest.title)
                            } else {
                                selectedItemsList.remove(interest)
                                selectedItemsNames.remove(interest.title)
                            }
                        } else {
                            interest.isSelected = false
                            if (selectedItemsList.contains(interest)) {
                                selectedItemsList.remove(interest)
                            }
                        }
                    },
                    isSelected = interest.isSelected
                )
            }
        )
    }
}

data class InterestsItem(val icon: Int, val title: String, var isSelected: Boolean = false)