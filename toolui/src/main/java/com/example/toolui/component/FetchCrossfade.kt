package com.example.toolui.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.toolui.state.FetchState

@Composable
fun <T> FetchCrossfade(
	state: FetchState<T>,
	onRetry: (() -> Unit)? = null,
	onLoading: @Composable () -> Unit = { LoadingComponent() },
	onError: @Composable (Throwable) -> Unit = {
		ErrorStateComponent(
			message = it.message,
			onClick = onRetry
		)
	},
	onSuccess: @Composable (T) -> Unit,
) {
	Crossfade(targetState = state) {
		when (it) {
			is FetchState.Success -> onSuccess(it.data)
			FetchState.Loading -> onLoading()
			is FetchState.Error -> onError(it.error)
		}
	}
}

@Composable
private fun LoadingComponent() {
	Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		CircularProgressIndicator()
	}
}

