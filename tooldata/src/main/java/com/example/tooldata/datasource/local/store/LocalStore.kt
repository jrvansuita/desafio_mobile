package com.example.tooldata.datasource.local.store

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow

class LocalStore<T>(private val dataStore: DataStore<T>) {
	fun getData(): Flow<T> {
		return dataStore.data
	}

	suspend fun setData(data: T) {
		dataStore.updateData {
			data
		}
	}

	suspend fun update(action: (T) -> T) {
		dataStore.updateData(action)
	}
}