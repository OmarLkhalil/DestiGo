package com.mobilebreakero.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.domain.model.PlaceItem
import com.mobilebreakero.domain.model.SearchItemModel
import com.mobilebreakero.domain.util.DataUtils.API_KEY

@Composable
fun SearchResultItem(
    item: LazyPagingItems<PlaceItem>,
    itemIndex : Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item[itemIndex]?.name?.let { Text(text = it, color = Color.Black) }

        Spacer(modifier = Modifier.height(10.dp))

        for(i in 0 until item.itemCount){
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${item[itemIndex]?.photos?.get(i)?.photoReference}&key=$API_KEY"
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = FillBounds,
                modifier = Modifier.size(100.dp),
                loading = {
                    LoadingIndicator()
                },
            )
        }
    }
}