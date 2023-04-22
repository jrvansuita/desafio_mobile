package com.example.demobycoders.ui.home

import android.location.Location
import androidx.lifecycle.viewModelScope
import com.example.demobycoders.template.FetchStateViewModel
import com.example.demobycoders.usecase.CurrentUserUseCase
import com.example.demobycoders.usecase.LogoutUseCase
import com.example.tooldata.datasource.local.store.LocalStore
import com.example.tooldata.model.User
import com.example.tooldata.usecase.GetCurrentLocationUseCase
import com.example.toolthirdparty.analytics.AnalyticsWrapper
import com.example.toolui.state.toFetchState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class HomeViewModel(
	private val currentUserUseCase: CurrentUserUseCase,
	private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
	private val analyticsWrapper: AnalyticsWrapper,
	private val logoutUseCase: LogoutUseCase,
	private val localStore: LocalStore<User>
) : FetchStateViewModel<HomeState>() {

	override suspend fun fetch() = getCurrentLocationUseCase().map {
		HomeState(position = LatLng(it.latitude, it.longitude)).apply {
			handleLocationInfo(it)
		}
	}.toFetchState()

	private fun handleLocationInfo(currentLocation: Location) = viewModelScope.launch {
		currentUserUseCase()?.let { currentUser ->
			analyticsWrapper.mapDisplayed(
				email = currentUser.email,
				lat = currentLocation.latitude,
				long = currentLocation.longitude
			)

			localStore.update {
				it.copy(
					lat = currentLocation.latitude,
					long = currentLocation.longitude
				)
			}
		}
	}

	fun onLogout() = viewModelScope.launch {
		logoutUseCase()
	}

}
