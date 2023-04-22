package com.example.demobycoders.template

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

private interface Emitter<T> {
	suspend fun emit(value: T)
	suspend fun close(): Boolean
}

private interface Receiver<T> {
	fun receiveAsFlow(): Flow<T>
}

class MutableEventChannel<T> : Emitter<T>, Receiver<T> {
	private val channel = Channel<T>(Channel.UNLIMITED)

	override suspend fun emit(value: T) = channel.send(value)

	override fun receiveAsFlow(): Flow<T> = channel.receiveAsFlow()

	override suspend fun close() = channel.close()
}