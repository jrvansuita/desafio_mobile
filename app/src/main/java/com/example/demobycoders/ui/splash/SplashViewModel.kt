package com.example.demobycoders.ui.splash

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.demobycoders.nav.Route
import com.example.demobycoders.template.SavedStateViewModel
import com.example.demobycoders.usecase.ReturningUserUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
	savedStateHandle: SavedStateHandle,
	returningUserUseCase: ReturningUserUseCase,
) : SavedStateViewModel<SplashState>(savedStateHandle) {

	override fun startsWith() = SplashState(true)

	init {
		viewModelScope.launch {
			if (returningUserUseCase()) {
				update { it.copy(keep = false, route = Route.Home) }
			} else {
				update { it.copy(keep = false, route = Route.Login) }
			}
		}
	}

}
