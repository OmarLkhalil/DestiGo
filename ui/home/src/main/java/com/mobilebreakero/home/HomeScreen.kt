package com.mobilebreakero.home

import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilebreakero.common_ui.components.GetUserFromFireStore
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.CREATE_TRIP
import com.mobilebreakero.data.repoimpl.GenerateRandomIdNumber
import com.mobilebreakero.domain.model.AppUser
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.model.TripsItem
import com.mobilebreakero.domain.util.Response
import com.mobilebreakero.home.components.AddButtonDesign
import com.mobilebreakero.home.components.ForYouItem
import com.mobilebreakero.home.components.PostItem
import com.mobilebreakero.home.components.TitleText
import com.mobilebreakero.home.components.TopScreenImage
import com.mobilebreakero.viewModel.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val user = remember { mutableStateOf(AppUser()) }
    val firebaseUser = Firebase.auth.currentUser

    GetUserFromFireStore(
        id = firebaseUser?.uid ?: "",
        user = { userId ->
            userId.id = firebaseUser?.uid
            user.value = userId
        }
    )

    val posts by viewModel.postsFlow.collectAsState()

    LaunchedEffect(posts) {
        viewModel.getPosts()
    }

    val userRecommended = viewModel.userRecommendations
    val userRecommendedPlaces = viewModel.userRecommendationsPlaces

    LaunchedEffect(user.value) {
        viewModel.getRecommendations(user.value.interestedPlaces ?: listOf())
        viewModel.getRecommendedPlaces(user.value.interestedPlaces ?: listOf())
    }

    var showPlacesBottomSheet by remember { mutableStateOf(false) }
    var showTripsBottomSheet by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var isTripSaved by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            (if (user.value.id != null) user.value.name else "user")?.let {
                TopScreenImage(
                    user = it,
                    navController = navController
                )
            }

            var selectedPlacesItemIndex by remember { mutableStateOf(-1) }
            var selectedTripsItemIndex by remember { mutableStateOf(-1) }
            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    model = R.drawable.travel,
                    contentDescription = "Travel",
                    loading = { LoadingIndicator() },
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "Get ready for your next trip!",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 1.2.em
                    )
                    Text(
                        text = "Explore the world's best Destinations, plan your trips \n and find your next adventure with DestiGo",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            navController.navigate(CREATE_TRIP)
                        },
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(text = "Let's Go!", fontSize = 11.sp, color = Color.Black)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            TitleText(text = "For You")
            Text(
                text = "DestiGo recommend trip plans based on your interests!",
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            LazyRow {
                items(userRecommended.size) { index ->
                    userRecommended[index]?.title?.let {
                        userRecommended[index]?.reasonForTravel?.let { it1 ->
                            ForYouItem(
                                title = it,
                                desc = it1,
                                image = userRecommended[index]?.image,
                                onItemClick = {
                                    selectedTripsItemIndex = index
                                    showTripsBottomSheet = true
                                },
                                isSaved = isTripSaved,
                                onSaveCLick = {
                                    viewModel.savePublicTrips(
                                        trip = TripsItem(
                                            id = userRecommended[index]?.id,
                                            userId = user.value.id,
                                            tripId = GenerateRandomIdNumber().toString(),
                                            title = userRecommended[index]?.title,
                                            image = userRecommended[index]?.image,
                                            fullJourney = userRecommended[index]?.fullJourney,
                                            placesToVisit = userRecommended[index]?.placesToVisit,
                                            description = userRecommended[index]?.description,
                                            reasonForTravel = userRecommended[index]?.reasonForTravel,
                                        )
                                    )
                                    Toast.makeText(
                                        context,
                                        "Trip saved! You can find it in your trips",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    isTripSaved = !isTripSaved
                                }
                            )
                        }
                    }
                }
            }
            val sheetState = rememberModalBottomSheetState()
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    model = R.drawable.destinations,
                    contentDescription = "Travel",
                    loading = { LoadingIndicator() },
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "DestiGo get the best places for you!",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 1.2.em,
                        color = Color.White
                    )
                    Text(
                        text = "Your are not lost, you are here! \n DestiGo get the best recommendations for you",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(
                                    scrollState.value + 800,
                                    spring(
                                        Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessVeryLow
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(text = "Let's Explore!", fontSize = 11.sp, color = Color.Black)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            TitleText(text = "Best destinations for you🌍")
            Spacer(modifier = Modifier.height(5.dp))
            LazyRow {
                items(userRecommendedPlaces.size) { index ->
                    userRecommendedPlaces[index]?.name?.let {
                        userRecommendedPlaces[index]?.category?.let { it1 ->
                            ForYouItem(
                                title = it,
                                desc = it1,
                                image = userRecommendedPlaces[index]?.image,
                                onItemClick = {
                                    selectedPlacesItemIndex = index
                                    showPlacesBottomSheet = true
                                },
                                onSaveCLick = {}
                            )
                        }
                    }

                }
            }

            if (showPlacesBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showPlacesBottomSheet = false
                    },
                    tonalElevation = 1.dp,
                    sheetState = sheetState,
                ) {
                    Column() {
                        SubcomposeAsyncImage(
                            model = userRecommendedPlaces[selectedPlacesItemIndex]?.image,
                            contentDescription = "place image",
                            modifier = Modifier
                                .padding(5.dp)
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillBounds,
                            loading = {
                                LoadingIndicator()
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = userRecommendedPlaces[selectedPlacesItemIndex]?.name ?: "",
                            modifier = Modifier.padding(10.dp),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(10.dp)
                                .border(.5.dp, Color(0xff4F80FF), RoundedCornerShape(20.dp))
                        ) {
                            Text(
                                text = "${userRecommendedPlaces[selectedPlacesItemIndex]?.city ?: ""}, ",
                                modifier = Modifier.padding(4.dp),
                                fontSize = 15.sp,
                            )
                            Text(
                                text = userRecommendedPlaces[selectedPlacesItemIndex]?.country
                                    ?: "",
                                modifier = Modifier.padding(4.dp),
                                fontSize = 15.sp,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(350.dp)
                                .wrapContentHeight()
                                .padding(10.dp)
                                .background(Color.Transparent)
                        ) {
                            Column(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(20.dp),
                                        clip = true,
                                        ambientColor = Color(0xFFEDF1FD)
                                    )
                                    .clip(RoundedCornerShape(20.dp))
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                                    .background(Color(0xFFEDF1FD))
                            ) {
                                Text(
                                    text = userRecommendedPlaces[selectedPlacesItemIndex]?.category
                                        ?: "",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userRecommendedPlaces[selectedPlacesItemIndex]?.description
                                        ?: "",
                                    modifier = Modifier.padding(5.dp),
                                    fontSize = 15.sp,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                        Text(
                            text = "The activity you can do here is",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 15.sp,
                        )
                        Text(
                            text = userRecommendedPlaces[selectedPlacesItemIndex]?.activity ?: "",
                            modifier = Modifier.padding(start = 10.dp, top = 3.dp, bottom = 3.dp),
                            fontSize = 18.sp,
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }
            }

            if (showTripsBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showTripsBottomSheet = false
                    },
                    tonalElevation = 1.dp,
                    sheetState = sheetState,
                ) {
                    Column() {
                        SubcomposeAsyncImage(
                            model = userRecommended[selectedTripsItemIndex]?.image,
                            contentDescription = "place image",
                            modifier = Modifier
                                .padding(5.dp)
                                .height(130.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillBounds,
                            loading = {
                                LoadingIndicator()
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = userRecommended[selectedTripsItemIndex]?.title ?: "",
                            modifier = Modifier.padding(10.dp),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(10.dp)
                                .border(.5.dp, Color(0xff4F80FF), RoundedCornerShape(20.dp))
                        ) {
                            Text(
                                text = "from ${userRecommended[selectedTripsItemIndex]?.fullJourney?.startDate ?: ""}, ",
                                modifier = Modifier.padding(4.dp),
                                fontSize = 15.sp,
                            )
                            Text(
                                text = "to ${userRecommended[selectedTripsItemIndex]?.fullJourney?.endDate ?: ""}, ",
                                modifier = Modifier.padding(4.dp),
                                fontSize = 15.sp,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(350.dp)
                                .wrapContentHeight()
                                .padding(10.dp)
                                .background(Color.Transparent)
                        ) {
                            Column(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(20.dp),
                                        clip = true,
                                        ambientColor = Color(0xFFEDF1FD)
                                    )
                                    .clip(RoundedCornerShape(20.dp))
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                                    .background(Color(0xFFEDF1FD))
                            ) {
                                Text(
                                    text = userRecommended[selectedTripsItemIndex]?.category
                                        ?: "",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userRecommended[selectedTripsItemIndex]?.description
                                        ?: "",
                                    modifier = Modifier.padding(5.dp),
                                    fontSize = 15.sp,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                        Text(
                            text = "The places you can visit here is",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 15.sp,
                        )
                        LazyRow(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(10.dp)
                        ) {
                            items(
                                userRecommended[selectedTripsItemIndex]?.placesToVisit?.size ?: 0
                            ) {
                                ForYouItem(
                                    title = userRecommended[selectedTripsItemIndex]?.placesToVisit?.get(
                                        it
                                    )?.name ?: "",
                                    desc = "",
                                    onSaveCLick = { /*TODO*/ },
                                    image = userRecommended[selectedTripsItemIndex]?.placesToVisit?.get(
                                        it
                                    )?.image ?: "",
                                    onItemClick = {}
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "The full Journey ",
                            modifier = Modifier.padding(5.dp),
                            fontSize = 15.sp,
                        )
                        Column {
                            Row {
                                ItemsChip(title = "Start Date ${userRecommended[selectedTripsItemIndex]?.fullJourney?.startDate ?: ""}") {}
                                ItemsChip(title = "End Date ${userRecommended[selectedTripsItemIndex]?.fullJourney?.endDate ?: ""}") {}
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .height(500.dp)
                    .fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    model = R.drawable.blogging,
                    contentDescription = "Travel",
                    loading = { LoadingIndicator() },
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "You can also post and share your trip with other travellers here!",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Take a look at the posts from other travellers and get inspired! \n and share your own trip...",
                        fontSize = 16.sp,
                        color = Color.White,
                        lineHeight = 1.2.em
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(
                                    scrollState.value + 800,
                                    spring(
                                        Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessVeryLow
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(text = "Let's take a look!", fontSize = 11.sp, color = Color.Black)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            TitleText(text = "Travellers Posts")
            Text(
                text = "You can see posts from other travellers on DestiGo!",
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn(
                modifier = Modifier
                    .height(450.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (posts) {

                    is Response.Loading -> {

                    }

                    is Response.Success -> {

                        val posts = (posts as Response.Success<List<Post>>).data

                        items(posts.size) { index ->
                            val context = LocalContext.current

                            var postsLikes by remember { mutableIntStateOf(posts[index].numberOfLikes) }

                            var isLiked by
                            remember { mutableStateOf(posts[index].likedUserIds.contains(user.value.id)) }

                            val userProfile = posts[index].userId

                            LaunchedEffect(Unit) {
                                postsLikes = posts[index].numberOfLikes
                            }
                            PostItem(
                                name = posts[index].userName!!,
                                numberOfLike = postsLikes,
                                location = posts[index].location!!,
                                imageUri = posts[index].image!!,
                                profilePhoto = posts[index].profilePhoto!!,
                                text = posts[index].text ?: "",
                                onLikeClick = {
                                    isLiked = !isLiked
                                    if (isLiked) {
                                        postsLikes += 1
                                    } else if (postsLikes > 0) {
                                        postsLikes -= 1
                                    }
                                    viewModel.likePost(
                                        postId = posts[index].id!!,
                                        userId = user.value.id!!,
                                        context = context,
                                        likes = postsLikes,
                                    )
                                },
                                onCommentClick = {
                                    navController.navigate("comment/${posts[index].id}")
                                },
                                onPostClick = {
                                    navController.navigate("postDetails/${posts[index].id}")
                                },
                                onProfileClick = {
                                    navController.navigate("profileDetails/${userProfile}")
                                },
                                onShareClick = {

                                    viewModel.sharePost(
                                        postId = posts[index].id!!,
                                        userId = user.value.id!!,
                                        userName = user.value.name!!,
                                    )

                                    when (viewModel.sharePostResponse) {

                                        is Response.Success -> {
                                            Toast.makeText(
                                                context,
                                                "Post shared successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        is Response.Failure -> {
                                            Toast.makeText(
                                                context,
                                                "Error sharing post",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        else -> {
                                        }
                                    }


                                },
                                isLiked = isLiked
                            )
                        }
                    }

                    else -> {}
                }

            }
        }
    }
    AddButtonDesign(navController = navController)
}

@Composable
fun ItemsChip(title: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(20.dp))
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable { onClick() }
            .border(.6.dp, Color(0xff4F80FF), RoundedCornerShape(20.dp))
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            modifier = Modifier.padding(10.dp),
        )
    }

}