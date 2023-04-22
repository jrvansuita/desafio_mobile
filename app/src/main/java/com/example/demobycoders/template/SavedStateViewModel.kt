package com.example.demobycoders.template

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class SavedStateViewModel<T : Parcelable>(
	private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	val stateFlow: StateFlow<T> by lazy {
		savedStateHandle.getStateFlow(STATE_KEY, startsWith())
	}

	val state: T
		get() = stateFlow.value

	fun update(action: (T) -> T) {
		savedStateHandle[STATE_KEY] = action(savedStateHandle.get<T>(STATE_KEY) ?: startsWith())
	}

	abstract fun startsWith(): T

	companion object {
		private const val STATE_KEY = "state"
	}
}