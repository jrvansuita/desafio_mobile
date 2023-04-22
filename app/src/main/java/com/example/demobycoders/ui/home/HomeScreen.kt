package com.example.demobycoders.ui.home

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.demobycoders.R
import com.example.demobycoders.ui.theme.DemoByCodersTheme
import com.example.tooldata.exception.LocationException
import com.example.toolui.component.ErrorStateComponent
import com.example.toolui.component.FetchCrossfade
import com.example.toolui.state.ButtonState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
private fun Preview() {
	DemoByCodersTheme {
		HomeComposable(state = HomeState(position = LatLng(-26.9318589, -48.9528489)))
	}
}

@Composable
fun HomeScreen(
	viewModel: HomeViewModel = koinViewModel(),
	onLogout: (() -> Unit)? = null
) {
	val scope = rememberCoroutineScope()
	val permissions = arrayOf(
		ACCESS_COARSE_LOCATION,
		ACCESS_FINE_LOCATION
	)
	val launcher = rememberLauncherForActivityResult(
		ActivityResultContracts.RequestMultiplePermissions()
	) {
		if (it.values.all { true }) scope.launch {
			viewModel.retry()
		}
	}

	LaunchedEffect(Unit) {
		scope.launch {
			launcher.launch(permissions)
		}
	}

	val fetchState by viewModel.fetchFlow.collectAsStateWithLifecycle()

	FetchCrossfade(state = fetchState, onError = {
		when (it) {
			is LocationException.PermissionsNotGranted -> ErrorStateComponent(
				message = stringResource(R.string.permission_not_granted)
			) {
				viewModel.retry()
			}
			is LocationException.ProvidersNotEnabled -> ErrorStateComponent(
				message = stringResource(R.string.location_disabled)
			) {
				viewModel.retry()
			}
			is LocationException.ProviderNotExists -> ErrorStateComponent(
				message = stringResource(R.string.location_not_supported),
				buttonState = ButtonState(enabled = false)
			)
			else -> ErrorStateComponent(message = it.localizedMessage)
		}
	}) {
		HomeComposable(state = it, onLogout = {
			viewModel.onLogout()
			onLogout?.invoke()
		})
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeComposable(state: HomeState, onLogout: (() -> Unit)? = null) {
	Scaffold(
		topBar = {
			TopAppBar(title = { Text(text = stringResource(R.string.home)) }, actions = {
				IconButton(onClick = { onLogout?.invoke() }) {
					Icon(
						imageVector = Icons.Default.Close,
						contentDescription = null
					)
				}
			})
		}
	) {

		GoogleMap(
			modifier = Modifier.fillMaxSize(),
			cameraPositionState = rememberCameraPositionState {
				position = CameraPosition.fromLatLngZoom(state.position, 15f)
			}
		) {
			Marker(
				state = MarkerState(position = state.position),
				title = stringResource(R.string.current_location),
			)
		}
	}
}