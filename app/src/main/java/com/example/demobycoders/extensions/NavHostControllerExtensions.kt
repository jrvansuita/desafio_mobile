package com.example.demobycoders.extensions

import androidx.navigation.NavHostController
import com.example.demobycoders.nav.Route

fun NavHostController.navigate(route: Route) = navigate(route.key)

fun NavHostController.navigateAndPop(route: Route) = navigate(route.key) {
	popUpTo(graph.startDestinationId) {
		inclusive = true
	}
	launchSingleTop = true
}
