package com.example.tooldata.di


import com.example.tooldata.usecase.GetCurrentLocationUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val usecaseModule = module {
	factoryOf(::GetCurrentLocationUseCase)
}

