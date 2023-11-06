package com.example.addpost.componant

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.addpost.R
import com.example.addpost.viewmodel.AddPostViewModel
import com.mobilebreakero.common_ui.navigation.NavigationRoutes.HOME_SCREEN
import com.mobilebreakero.domain.model.Post
import com.mobilebreakero.domain.util.DataUtils
import com.mobilebreakero.domain.util.Response


@Composable
fun AddPostCard(navController: NavController, viewModel: AddPostViewModel = hiltViewModel()) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val user = DataUtils.user

    Box (
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color(0xFF4F80FF),
                shape = RoundedCornerShape(18.dp),
            )
            .width(350.dp)
            .height(600.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
        ) {
            TitleText()
            AddPostButtons(
                onClick = {
                    //Todo
                },
                iconId = R.drawable.location,
                description = "Location Icon",
                text = "Your Location"
            )
            AddPostButtons(
                onClick = {
                    launcher.launch("image/*")
                },
                iconId = R.drawable.photo,
                description = "Photo Icon",
                text = "Upload Photo"
            )
            AsyncImage(
                model = imageUri,
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(bottomEnd = 25.dp, bottomStart = 25.dp))
            )
            var post = Post(
                id = user?.id + 1,
                userId = user?.id,
                userName = user?.name,
                image = imageUri
            )
            PostOrCancelSection(navController = navController, viewModel, post)
        }
    }
}

@Composable
fun TitleText() {
    Text(
        text = "Add Post",
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        modifier = Modifier.padding(12.dp)
    )
}

@Composable
fun AddPostButtons(
    onClick: ()-> Unit,
    iconId: Int,
    description: String,
    text:String
) {
    Button(
        onClick = onClick,
        border = BorderStroke(1.dp, Color(0xff4F80FF)),
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = description,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF4F80FF)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = text,
                color = Color.LightGray,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun PostOrCancelSection(navController: NavController, viewModel: AddPostViewModel, post: Post) {

    val context = LocalContext.current

    Row (
        horizontalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.width(40.dp))
        ScreenButton(
            onClick = {
                viewModel.addPost(post)
                Log.e("Post", post.userName!!)
                if (viewModel.addPostResponse == Response.Success(true)) {
                    Toast.makeText(
                        context,
                        "Post Added",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(route = HOME_SCREEN)
                }  else {
                    Toast.makeText(
                        context,
                        "Field",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            buttonColor = Color(0xFF4F80FF),
            text = "Post"
        )
        Spacer(modifier = Modifier.width(20.dp))
        ScreenButton(
            onClick = { navController.navigate(HOME_SCREEN) },
            border = BorderStroke(1.dp, Color(0xff4F80FF)),
            text = "Cancel",
            textColor = Color.Black
        )
    }
}

@Composable
fun ScreenButton(
    onClick: ()-> Unit,
    buttonColor: Color? = Color.White,
    text: String,
    border: BorderStroke? = null,
    textColor: Color? = Color.White
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(buttonColor!!),
        border = border
    ) {
        Text(text = text, fontSize = 18.sp, color = textColor!!)
    }
}