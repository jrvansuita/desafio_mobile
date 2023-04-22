package com.example.demobycoders.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun <T> Flow<T>.collectWithLifecycle(
	owner: LifecycleOwner = LocalLifecycleOwner.current,
	lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
	onCollect: (T) -> Unit
) = collectWithLifecycleScope(owner, lifecycleState, onCollect)

fun <T> Flow<T>.collectWithLifecycleScope(
	owner: LifecycleOwner,
	lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
	onCollect: (T) -> Unit
) {
	owner.lifecycleScope.launch {
		owner.repeatOnLifecycle(lifecycleState) {
			collect { onCollect(it) }
		}
	}
}