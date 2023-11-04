package com.mobilebreakero.interestedplaces.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobilebreakero.common_ui.components.AuthButton
import com.mobilebreakero.interestedplaces.InterestedPlacesViewModel
import com.mobilebreakero.interestedplaces.components.GreetingSection
import com.mobilebreakero.interestedplaces.components.VerticalGrid
import com.mobilebreakero.interestedplaces.components.getUserLocation
import com.mobilebreakero.interestedplaces.components.selectedItemsList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun InterestedPlacesScreen(navController: NavController) {

    InterestedScreenContent(
        navController = navController,
        currentLocation = getUserLocation(context = LocalContext.current)
    )

}

@Composable
fun InterestedScreenContent(
    navController: NavController,
    viewModel: InterestedPlacesViewModel = hiltViewModel(),
    currentLocation: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        GreetingSection()
        Spacer(modifier = Modifier.height(10.dp))
        VerticalGrid()
        val context = LocalContext.current

        val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

        val coroutineScope = rememberCoroutineScope { ioDispatcher }

        val selectedItemsList = selectedItemsList

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AuthButton(
                onClick = {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return@AuthButton
                    }
                    if (selectedItemsList.size > 0) {
                        for (item in selectedItemsList) {
                            coroutineScope.launch {
                                viewModel.getSearchResultStream(
                                    location = currentLocation,
                                    keyword = item.title,
                                    language = "en",
                                    radius = 100000,
                                    type = item.title,
                                )
                            }
                        }
                    }
                },
                buttonColor = Color(0xff4F80FF),
                text = "Continue",
                modifier = Modifier
                    .shadow(elevation = 0.dp, shape = CircleShape)
                    .width(270.dp)
                    .height(50.dp)
                    .wrapContentHeight()
                    .padding(horizontal = 20.dp, vertical = 2.dp)
            )
        }

    }
}
