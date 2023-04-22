package com.example.tooldata.exception

sealed class LocationException {
	class PermissionsNotGranted : Throwable()
	class ProvidersNotEnabled : Throwable()
	class ProviderNotExists : Throwable()
}