package com.mobilebreakero.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.FillBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.domain.model.DataItem
import com.mobilebreakero.domain.model.PhotoDataItem
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.search.SearchViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultItem(
    item: DataItem,
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {

    LaunchedEffect(item) {
        item.locationId?.let { viewModel.getPhoto(it) }
    }

    val photos by viewModel.photo.collectAsState()

    Box(
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
            .background(Color.Transparent)
    ) {

        Column(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = true,
                    ambientColor = Color(0xFFD5E1FF)
                )
                .height(150.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .clickable {
                    navController.navigate("details/${item.locationId}")
                }
                .background(Color(0xFFD5E1FF))
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.width(135.dp))
                item.name?.let {
                    Text(
                        text = it,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }
            }
        }

        when (photos) {

            is Response.Success -> {
                val results =
                    (photos as Response.Success<List<PhotoDataItem?>>).data.filter { it?.id.toString() == item.locationId }
                VerticalPager(
                    state = rememberPagerState(),
                    modifier = Modifier.fillMaxWidth(),
                    pageCount = results.size
                ) { page ->
                    val photoOfEachOne = results[page]?.images?.large?.url

                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoOfEachOne)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier
                            .height(170.dp)
                            .width(125.dp)
                            .align(Alignment.CenterStart)
                            .clip(RoundedCornerShape(15.dp)),
                        contentDescription = null,
                        contentScale = FillBounds,
                        loading = {
                            LoadingIndicator()
                        },
                    )
                }
            }

            is Response.Failure -> {
                (photos as Response.Failure).e.message?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }
            }

            else -> {
                LoadingIndicator()
            }
        }

    }
}
