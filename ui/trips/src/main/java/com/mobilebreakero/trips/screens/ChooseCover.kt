package com.mobilebreakero.trips.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.TRIPS_SCREEN
import com.mobilebreakero.domain.util.DataUtils
import com.mobilebreakero.trips.TripsViewModel
import com.mobilebreakero.trips.components.CreateTripButton
import com.mobilebreakero.trips.screens.plan.tripId


@Composable
fun ChooseCoverScreen(
    navController: NavController,
    viewModel: TripsViewModel = hiltViewModel(),
    tripId: String
) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val uploadProgress by remember { mutableStateOf(0f) }
    var newPhotoUrl by remember { mutableStateOf("") }
    var imageLink by remember { mutableStateOf(newPhotoUrl) }
    var isUploading by remember { mutableStateOf(false) }

    LaunchedEffect(newPhotoUrl) {
        newPhotoUrl = imageLink
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Choose A Cover Photo",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Start)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        val context = LocalContext.current
        CoverImage(
            contentDescription = "Cover Image",
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .height(300.dp)
                .width(280.dp)
                .align(CenterHorizontally)
                .clickable {
                    launcher.launch("image/*")
                },
            data = Uri.parse(newPhotoUrl),
            contentScale = ContentScale.FillBounds,
        )

        if (imageUri != null) {
            isUploading = true
            uploadImageToStorage(imageUri) { downloadUrl, isSuccessful ->
                isUploading = false
                if (isSuccessful as Boolean) {
                    imageLink = downloadUrl
                    newPhotoUrl = imageLink
                    viewModel.addPhoto(photo = imageLink, id = tripId)
                    imageUri = null
                }
            }
        }

        if (isUploading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                progress = uploadProgress
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        CreateTripButton(text = "Next",
            buttonColor = Color(0xff4F80FF),
            modifier = Modifier
                .align(CenterHorizontally)
                .height(50.dp)
                .width(320.dp),
            onClick = {
                Toast.makeText(context, "Trip Created Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(TRIPS_SCREEN)
            }
        )
    }
}


fun uploadImageToStorage(uri: Uri?, onComplete: (String, Any?) -> Unit) {
    val store = Firebase.storage
    val storageRef = store.reference
    val imageRef = storageRef.child("tripCover/${DataUtils.user?.id}${tripId}}")

    if (uri == null) {
        onComplete("", false)
        return
    }

    val uploadTask = imageRef.putFile(uri)

    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUri = task.result.toString()
            onComplete(downloadUri, true)
        } else {
            onComplete("", false)
        }
    }
}

@Composable
fun CoverImage(
    data: Uri? ,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale,
) {
    Box(
        modifier = modifier
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            loading = {
                LoadingIndicator()
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.1f))
        )
    }
}