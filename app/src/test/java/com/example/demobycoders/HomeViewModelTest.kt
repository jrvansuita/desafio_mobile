package com.example.demobycoders

import android.location.Location
import app.cash.turbine.test
import com.example.demobycoders.ui.home.HomeViewModel
import com.example.demobycoders.usecase.CurrentUserUseCase
import com.example.demobycoders.usecase.LogoutUseCase
import com.example.tooldata.datasource.local.store.LocalStore
import com.example.tooldata.model.User
import com.example.tooldata.usecase.GetCurrentLocationUseCase
import com.example.toolthirdparty.analytics.AnalyticsWrapper
import com.example.toolui.state.FetchState
import com.example.toolui.state.current
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

	private lateinit var viewModel: HomeViewModel
	private val latLong = LatLng(-26.9318589, -48.9528489)
	private val location: Location = mockk()
	private val currentUserUseCase: CurrentUserUseCase = mockk()
	private val getCurrentLocationUseCase: GetCurrentLocationUseCase = mockk()
	private val analyticsWrapper: AnalyticsWrapper = mockk()
	private val localStore: LocalStore<User> = mockk()
	private val logoutUseCase: LogoutUseCase = mockk()
	private val testDispatcher: TestDispatcher = StandardTestDispatcher()

	@Before
	fun starting() {
		Dispatchers.setMain(testDispatcher)

		viewModel = HomeViewModel(
			currentUserUseCase = currentUserUseCase,
			getCurrentLocationUseCase = getCurrentLocationUseCase,
			analyticsWrapper = analyticsWrapper,
			logoutUseCase = logoutUseCase,
			localStore = localStore
		)
	}

	@After
	fun ending() = Dispatchers.resetMain()

	@Test
	fun `WHEN init fetch current location`() = runTest {
		val user = User("teste@email.com", latLong.latitude, latLong.longitude)
		coEvery { location.latitude } returns latLong.latitude
		coEvery { location.longitude } returns latLong.longitude
		coEvery { getCurrentLocationUseCase() } returns Result.success(location)
		coEvery { currentUserUseCase() } returns user
		coEvery { analyticsWrapper.mapDisplayed(any(), any(), any()) } returns Unit
		coEvery { localStore.update(any()) } returns Unit

		viewModel.fetchFlow.test {
			with(awaitItem()) {
				assertEquals(this, FetchState.Loading)

				val item = awaitItem()
				assertEquals(item.current().position.latitude, latLong.latitude)
				assertEquals(item.current().position.longitude, latLong.longitude)

				coVerify(exactly = 1) { getCurrentLocationUseCase() }
				coVerify(exactly = 1) { currentUserUseCase() }
				coVerify(exactly = 1) { localStore.update(any()) }
				coVerify(exactly = 1) {
					analyticsWrapper.mapDisplayed(
						eq(user.email),
						eq(latLong.latitude),
						eq(latLong.longitude)
					)
				}
			}
		}
	}
}