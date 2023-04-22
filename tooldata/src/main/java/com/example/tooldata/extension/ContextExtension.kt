package com.example.tooldata.extension

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat

fun Context.locationService() = getSystemService(Context.LOCATION_SERVICE) as LocationManager

val Context.locationPermissionSet: Array<String>
	get() = arrayOf(
		ACCESS_FINE_LOCATION,
		ACCESS_COARSE_LOCATION
	)

fun Context.isLocationPermissionGranted() = locationPermissionSet.all {
	ActivityCompat.checkSelfPermission(
		this,
		it
	) == PackageManager.PERMISSION_GRANTED
}