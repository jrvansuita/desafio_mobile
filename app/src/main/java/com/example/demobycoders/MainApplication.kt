package com.example.demobycoders

import android.app.Application
import com.example.demobycoders.di.appModule
import com.example.tooldata.di.dataModule
import com.example.toolthirdparty.di.thirdPartyModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@MainApplication)
			modules(
				appModule,
				dataModule,
				thirdPartyModule
			)
		}
	}
}