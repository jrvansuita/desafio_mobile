package com.example.demobycoders.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface EventEmitterViewModel<T> {
	val action: Flow<T>

	suspend fun emit(value: T)
}

class EventEmitterViewModelImpl<T> : EventEmitterViewModel<T>, ViewModel() {

	private val mutableEventChannel = MutableEventChannel<T>()

	override val action = mutableEventChannel.receiveAsFlow()

	override suspend fun emit(value: T) = mutableEventChannel.emit(value)

	override fun onCleared() {
		super.onCleared()

		viewModelScope.launch { mutableEventChannel.close() }
	}
}
