package com.mobilebreakero.details.components

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.domain.model.DetailsResponse
import com.mobilebreakero.domain.model.PhotoDataItem


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsContent(photos: List<PhotoDataItem?>, detailsResponse: DetailsResponse) {

    val pagerState = rememberPagerState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                pageCount = photos.size
            ) { page ->
                val photo = photos[page]?.images?.large?.url
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = photo,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        loading = { LoadingIndicator() })
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.2f))
                    )
                }
            }

            Row(
                Modifier
                    .height(24.dp)
                    .padding(start = 4.dp)
                    .width(100.dp)
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.Start
            ) {
                repeat(photos.size) { iteration ->
                    val lineWeight = animateFloatAsState(
                        targetValue = if (pagerState.currentPage == iteration) {
                            1.0f
                        } else {
                            if (iteration < pagerState.currentPage) {
                                0.5f
                            } else {
                                0.5f
                            }
                        }, label = "size", animationSpec = tween(300, easing = EaseInOut)
                    )
                    val color =
                        if (pagerState.currentPage == iteration) Color(0xFF4F80FF) else Color.White
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color)
                            .weight(lineWeight.value)
                            .height(10.dp)
                    )
                }
            }
        }

        Text(
            text = detailsResponse.name ?: "",
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = detailsResponse.rankingData?.rankingString ?: "",
            fontSize = 16.sp,
            modifier = Modifier.padding(3.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        DetailsCard(
            title = "Location Details",
            details = detailsResponse.addressObj?.addressString ?: ""
        ) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        DetailsCard(title = "About", details = detailsResponse.description ?: "") {
            Spacer(modifier = Modifier.height(20.dp))
        }

        AmenitiesCard(title = "About", details = detailsResponse.amenities ?: listOf("")) {
            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}


@Composable
fun ElevatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    icon: Int
) {
    Box(
        modifier = modifier
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF4F80FF))
            .height(40.dp)
            .width(120.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White
            )

            Text(
                text = title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}