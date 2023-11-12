package com.mobilebreakero.common_ui.components

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mobilebreakero.common_ui.R

@Composable
fun MapView(
    selectedLocation: String,
    onLocationSelected: (String) -> Unit,
    context: Context
) {
    var selectedMarker: Marker? by remember { mutableStateOf(null) }
    val geocoder = Geocoder(context)

    AndroidView(
        factory = { contextt ->
            val mapView = View.inflate(contextt, R.layout.map, null) as MapView
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

    SideEffect {
        val latLng = selectedLocation.split(",").run {
            if (size == 2) {
                LatLng(get(0).toDouble(), get(1).toDouble())
            } else {
                null
            }
        }

        if (latLng != null) {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val country = addresses[0].countryName
                    val government = addresses[0].adminArea
                    val fullAddress = "$government, $country"
                }
            }
        }
    }
}