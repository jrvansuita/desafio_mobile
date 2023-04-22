package com.example.demobycoders.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toolui.state.FetchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class FetchStateViewModel<F> : ViewModel() {

	private val mutableStateFlow by lazy {
		MutableStateFlow<FetchState<F>>(FetchState.Loading).apply {
			viewModelScope.launch {
				emit(fetch())
			}
		}
	}

	val fetchFlow: StateFlow<FetchState<F>> by lazy { mutableStateFlow.asStateFlow() }

	open fun retry() {
		viewModelScope.launch {
			mutableStateFlow.emit(FetchState.Loading)
			mutableStateFlow.emit(fetch())
		}
	}

	suspend fun loadingState() = mutableStateFlow.emit(FetchState.Loading)
	abstract suspend fun fetch(): FetchState<F>
}

