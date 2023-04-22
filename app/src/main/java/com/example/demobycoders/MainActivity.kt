package com.example.demobycoders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demobycoders.extensions.navigateAndPop
import com.example.demobycoders.nav.Route
import com.example.demobycoders.ui.home.HomeScreen
import com.example.demobycoders.ui.login.LoginScreen
import com.example.demobycoders.ui.splash.SplashViewModel
import com.example.demobycoders.ui.theme.DemoByCodersTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

	private val splashViewModel by viewModel<SplashViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		val splashScreen = installSplashScreen()
		super.onCreate(savedInstanceState)

		splashScreen.setKeepOnScreenCondition { splashViewModel.state.keep }

		setContent {
			DemoByCodersTheme {
				val navController = rememberNavController()
				val state by splashViewModel.stateFlow.collectAsState()

				NavHost(
					navController = navController,
					startDestination = state.route.key
				) {
					composable(Route.Login.key) {
						LoginScreen(onNavigateToHome = {
							navController.navigateAndPop(Route.Home)
						})
					}
					composable(Route.Home.key) {
						HomeScreen(onLogout = {
							navController.navigateAndPop(Route.Login)
						})
					}
				}

			}
		}
	}
}

