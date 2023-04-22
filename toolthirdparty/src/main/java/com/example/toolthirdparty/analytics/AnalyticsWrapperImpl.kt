package com.example.toolthirdparty.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase


internal class AnalyticsWrapperImpl : AnalyticsWrapper {

	private val firebaseAnalytics by lazy {
		Firebase.analytics.setAnalyticsCollectionEnabled(true)
		Firebase.analytics
	}

	override fun login(email: String) {
		firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, Bundle().apply {
			putString(FirebaseAnalytics.Param.METHOD, "email")
			putString(EMAIL, email)
		})
	}

	override fun mapDisplayed(email: String, lat: Double, long: Double) {
		firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS, Bundle().apply {
			putString(EMAIL, email)
			putDouble(LATITUDE, lat)
			putDouble(LONGITUDE, long)
		})
	}

	companion object {
		private const val EMAIL = "EMAIL"
		private const val LATITUDE = "LATITUDE"
		private const val LONGITUDE = "LONGITUDE"
	}


}