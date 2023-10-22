package com.mobilebreakero.search.components


import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mobilebreakero.search.R

@Composable
fun MapView(
    selectedLocation: String,
    onLocationSelected: (String) -> Unit
) {
    var selectedMarker: Marker? by remember { mutableStateOf(null) }

    AndroidView(
        factory = { context ->
            val mapView = View.inflate(context, R.layout.map, null) as MapView
            mapView.onCreate(Bundle())
            mapView.onResume()
            mapView.getMapAsync { googleMap ->
                googleMap.setOnMapClickListener { latLng ->
                    val latitude = latLng.latitude
                    val longitude = latLng.longitude

                    val newLocation = "$latitude,$longitude"
                    onLocationSelected(newLocation)
                    selectedMarker?.remove()
                    selectedMarker = googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )
                }
            }
            mapView
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}
