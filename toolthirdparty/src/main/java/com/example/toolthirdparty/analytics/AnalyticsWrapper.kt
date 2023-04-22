package com.example.toolthirdparty.analytics

interface AnalyticsWrapper {
	fun login(email: String)
	fun mapDisplayed(email: String, lat: Double, long: Double)
}