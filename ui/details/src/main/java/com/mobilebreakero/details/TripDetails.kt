package com.mobilebreakero.details

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mobilebreakero.common_ui.components.LoadingIndicator
import com.mobilebreakero.common_ui.components.calculateEndDate
import com.mobilebreakero.data.repoimpl.GenerateRandomIdNumber
import com.mobilebreakero.details.components.ItemsChip
import com.mobilebreakero.details.components.PlacesToVisit
import com.mobilebreakero.details.components.TripCheckList
import com.mobilebreakero.details.components.TripDetailsCard
import com.mobilebreakero.details.components.TripImages
import com.mobilebreakero.details.components.TripJournal
import com.mobilebreakero.domain.model.Trip
import com.mobilebreakero.domain.util.DataUtils
import com.mobilebreakero.domain.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun TripDetailsScreen(
    tripId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavController
) {

    LaunchedEffect(key1 = viewModel) {
        viewModel.getTripDetailsResult(tripId)
    }

    val tripDetails by viewModel.tripDetailsResult.collectAsState()

    when (tripDetails) {
        is Response.Success -> {
            val results = (tripDetails as Response.Success<Trip>).data
            TripDetails(results, navController)
        }

        is Response.Failure -> {

        }

        else -> {
            Response.Loading
            LoadingIndicator()
        }
    }

}

@Composable
fun TripDetails(
    trip: Trip,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    var isUpdatingTrip by remember { mutableStateOf(false) }
    var tripNameUpdate by remember { mutableStateOf("") }
    var tripDaysUpdate by remember { mutableStateOf("") }
    var tripDateUpdate by remember { mutableStateOf("") }

    TripDetailsCard(title = trip.name ?: "Trip details", onEditClick = {
        isUpdatingTrip = true
    }, content = {

        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            imageUri = uri
        }

        var currentProgress by remember { mutableFloatStateOf(0f) }
        var newPhotoUrl by remember { mutableStateOf(trip.image) }
        var imageLink by remember { mutableStateOf("") }
        var isUploading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .align(Alignment.TopCenter)
                ) {

                    SubcomposeAsyncImage(
                        model = Uri.parse(newPhotoUrl),
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                launcher.launch("image/*")
                            },
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

            if (imageUri != null && !isUploading) {
                isUploading = true
                uploadImageToStorage(imageUri) { downloadUrl, isSuccessful ->
                    if (isSuccessful as Boolean) {
                        newPhotoUrl = downloadUrl
                        imageLink = newPhotoUrl.toString()
                        viewModel.addPhoto(photo = imageLink, id = trip.id ?: "")
                        isUploading = false
                        imageUri = null
                    }
                }
            }

            LaunchedEffect(isUploading) {
                scope.launch {
                    loadProgress { progress ->
                        currentProgress = progress
                    }
                }
            }

            if (isUploading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = Color.Blue,
                    progress = currentProgress
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .horizontalScroll(rememberScrollState())
                ) {
                    ItemsChip(title = "From ${trip.startDate ?: ""}") {

                    }

                    var endDate by remember { mutableStateOf("") }
                    var newDays by remember { mutableStateOf("") }

                    if (tripDaysUpdate.isNotEmpty()) {
                        newDays = tripDaysUpdate
                    } else {
                        newDays = trip.duration ?: ""
                    }
                    if (tripDateUpdate.isNotEmpty())
                        LaunchedEffect(tripDateUpdate) {
                            endDate = calculateEndDate(trip.startDate ?: "", newDays)
                        }
                    else
                        endDate = trip.endDate ?: ""

                    ItemsChip(title = "To $endDate") {
                    }
                }

                Text(
                    text = "Places to visit",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Here's a list of places you saved to visit during your trip.",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                PlacesToVisit(trip = trip, navController = navController)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Your Trip Journal", fontSize = 20.sp, color = Color.Black)
                Text(
                    text = "Here you can document your trip and save your best memories.",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                TripJournal(trip = trip, navController = navController)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Your Trip Images", fontSize = 20.sp, color = Color.Black)
                Text(
                    text = "Here you can save your best trip images",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                TripImages(trip = trip, navController = navController)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Your Trip CheckList", fontSize = 20.sp, color = Color.Black)
                Text(
                    text = "Here's your stuff you need to pack for your trip",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(start = 10.dp, bottom = 6.dp)
                )
                TripCheckList(trip = trip, navController = navController)
            }
        }

        if (isUpdatingTrip) {
            AlertDialog(
                onDismissRequest = {
                    isUpdatingTrip = false
                },
                title = { Text("Update Trip") },
                text = {
                    Column(
                        verticalArrangement = spacedBy(10.dp),
                    ) {
                        OutlinedTextField(
                            value = tripNameUpdate,
                            onValueChange = { tripNameUpdate = it },
                            label = { Text("New trip name") }
                        )
                        OutlinedTextField(
                            value = tripDateUpdate,
                            visualTransformation = DateTransformation(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = { tripDateUpdate = it },
                            label = { Text("New trip start date") }
                        )
                        OutlinedTextField(
                            value = tripDaysUpdate,
                            onValueChange = { tripDaysUpdate = it },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text("New trip stay days ") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F80FF)),
                        onClick = {
                            if (tripNameUpdate.isNotEmpty()) {
                                viewModel.updateTripName(tripNameUpdate, trip.id ?: "")
                            }
                            if (tripDateUpdate.isNotEmpty()) {
                                val formattedDate = formatDate(tripDateUpdate)
                                if (formattedDate != null) {
                                    viewModel.updateTripDate(formattedDate, trip.id ?: "")
                                }
                            }
                            if (tripDaysUpdate.isNotEmpty()) {
                                viewModel.updateTripDays(tripDaysUpdate, trip.id ?: "")
                            }
                            isUpdatingTrip = false
                        }
                    ) {
                        Text("Update")
                    }
                }
            )
        }
    })
}


fun uploadImageToStorage(uri: Uri?, onComplete: (String, Any?) -> Unit) {
    val store = Firebase.storage
    val storageRef = store.reference
    val imageRef = storageRef.child("tripCover/${DataUtils.user?.id}${GenerateRandomIdNumber()}}")

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

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}

class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

fun formatDate(userInput: String): String? {
    val inputFormat = SimpleDateFormat("MMddyyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    return try {
        val parsedDate = inputFormat.parse(userInput)
        parsedDate?.let { outputFormat.format(it) }
    } catch (e: ParseException) {
        userInput
    }
}

fun formatMonthDisplay(input: String): String {
    return if (input.isNotEmpty()) {
        val monthString = input.substring(0, minOf(input.length, 2))
        val month = monthString.toIntOrNull()

        if (month != null && month in 1..12) {
            input
        } else {
            "12" + input.substring(2)
        }
    } else {
        input
    }
}


fun dateFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 2 == 1 && i < 4) out += "/"
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset + 1
            if (offset <= 8) return offset + 2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            return 8
        }
    }

    val formattedText = formatMonthDisplay(out)

    return TransformedText(AnnotatedString(formattedText), numberOffsetTranslator)
}