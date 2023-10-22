package com.mobilebreakero.trips.planchecklist

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.mobilebreakero.trips.R

@Composable
fun PlanCheckListScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.fragment_plan_check_list, null, false)

                view
            },
            update = {
            }
        )
    }
}