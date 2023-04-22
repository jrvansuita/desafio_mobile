package com.example.tooldata.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.example.tooldata.datasource.local.location.LocationProvider
import com.example.tooldata.datasource.local.location.LocationProviderImpl
import com.example.tooldata.datasource.local.store.LocalStore
import com.example.tooldata.model.User
import com.example.tooldata.selializer.DataSerializer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val USER_DATA = "USER_DATA"

internal val localDataModule = module {
	single<LocalStore<User>> { LocalStore(get()) }

	single<DataStore<User>> {
		DataStoreFactory.create(
			serializer = get(),
			produceFile = { androidContext().dataStoreFile(USER_DATA) },
		)
	}

	factory<Serializer<User>> {
		DataSerializer(
			User(),
			User::class
		)
	}

	factory<LocationProvider> { LocationProviderImpl(androidContext()) }
}