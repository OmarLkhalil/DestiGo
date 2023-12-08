package com.mobilebreakero.common_ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.concurrent.TimeUnit


lateinit var locationCallback: LocationCallback

lateinit var locationProvider: FusedLocationProviderClient

@SuppressLint("MissingPermission")
@Composable
fun getUserLocation(context: Context, onLocationGet: (String) -> Unit): String {

    locationProvider = LocationServices.getFusedLocationProviderClient(context)

    var currentUserLocation by remember { mutableStateOf(LatandLong()) }
    val geocoder = Geocoder(context)

    DisposableEffect(key1 = locationProvider) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {

                for (location in result.locations) {
                    currentUserLocation = LatandLong(location.latitude, location.longitude)
                }

                locationProvider.lastLocation
                    .addOnSuccessListener { location ->
                        location?.let {
                            val lat = location.latitude
                            val long = location.longitude
                            currentUserLocation = LatandLong(latitude = lat, longitude = long)
                            val fullAddress = getFullAddress(context, lat, long)
                            onLocationGet(fullAddress)
                        }
                    }
                    .addOnFailureListener {
                    }
            }

            private fun getFullAddress(context: Context, lat: Double, long: Double): String {

                val addresses = geocoder.getFromLocation(lat, long, 1)
                if (!addresses.isNullOrEmpty()) {
                    val country = addresses[0].countryName
                    val government = addresses[0].adminArea
                    val fullAddress = "$government, $country"
                    return fullAddress
                } else {
                    return "null"
                }

            }
        }
        locationUpdate()


        onDispose {
            stopLocationUpdate()
        }
    }

    return "${currentUserLocation.latitude},${currentUserLocation.longitude}"

}

fun stopLocationUpdate() {
    try {
        val removeTask = locationProvider.removeLocationUpdates(locationCallback)
        removeTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
            } else {
            }
        }
    } catch (se: SecurityException) {

    }
}

@SuppressLint("MissingPermission")
fun locationUpdate() {
    locationCallback.let {
        val locationRequest: LocationRequest =
            LocationRequest.create().apply {
                interval = TimeUnit.SECONDS.toMillis(60)
                fastestInterval = TimeUnit.SECONDS.toMillis(30)
                maxWaitTime = TimeUnit.MINUTES.toMillis(2)
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        locationProvider.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}

data class LatandLong(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)