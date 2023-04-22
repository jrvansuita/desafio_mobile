package com.example.tooldata.di


import org.koin.dsl.module

val dataModule = module {
	includes(
		localDataModule,
		usecaseModule,
	)
}