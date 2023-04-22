package com.example.tooldata.usecase

import com.example.tooldata.datasource.local.location.LocationProvider

class GetCurrentLocationUseCase(
	private val locationProvider: LocationProvider
) {
	suspend operator fun invoke() = locationProvider.getCurrent()
}