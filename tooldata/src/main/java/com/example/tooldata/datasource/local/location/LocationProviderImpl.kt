package com.example.tooldata.datasource.local.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.core.location.LocationManagerCompat
import com.example.tooldata.exception.LocationException
import com.example.tooldata.extension.isLocationPermissionGranted
import com.example.tooldata.extension.locationService
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration.Companion.seconds

private const val TIMEOUT_WAIT = 30

@SuppressLint("MissingPermission")
internal class LocationProviderImpl(private val context: Context) : LocationProvider {

	private val manager by lazy { context.locationService() }

	override suspend fun getCurrent(): Result<Location> = withTimeoutOrNull(TIMEOUT_WAIT.seconds) {
		suspendCoroutine<Result<Location>> {
			if (context.isLocationPermissionGranted()) {
				if (LocationManagerCompat.isLocationEnabled(manager)) {
					try {
						LocationServices.getFusedLocationProviderClient(context)
							.getCurrentLocation(
								Priority.PRIORITY_HIGH_ACCURACY,
								CancellationTokenSource().token
							)
							.addOnSuccessListener { location ->
								it.resume(Result.success(location))
							}.addOnFailureListener { _ ->
								it.resume(Result.failure(LocationException.ProviderNotExists()))
							}
					} catch (e: Exception) {
						it.resume(Result.failure(LocationException.ProviderNotExists()))
					}
				} else {
					it.resume(Result.failure(LocationException.ProvidersNotEnabled()))
				}
			} else {
				it.resume(Result.failure(LocationException.PermissionsNotGranted()))
			}
		}
	} ?: Result.failure(LocationException.ProviderNotExists())
}