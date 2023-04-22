package com.example.demobycoders.ui.splash

import android.os.Parcelable
import com.example.demobycoders.nav.Route
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashState(
	val keep: Boolean = true,
	val route: Route = Route.Login
) : Parcelable
