package com.mobilebreakero.trips.plan

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.mobilebreakero.trips.R

@Composable
fun PlanScreen(
    navController: NavController
){
    val buttonNext = remember { mutableStateOf<Button?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.fragment_plan, null, false)
                val button = view.findViewById<Button>(R.id.btn_next)
                buttonNext.value = button
                view
            },
            update = {
            }

        )
    }

    buttonNext.value?.setOnClickListener {
        navController.navigate("PlanCheckList")
    }
}