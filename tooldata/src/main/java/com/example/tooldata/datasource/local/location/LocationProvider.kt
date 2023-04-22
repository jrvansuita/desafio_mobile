package com.example.tooldata.datasource.local.location

import android.location.Location

interface LocationProvider {
	suspend fun getCurrent(): Result<Location>
}