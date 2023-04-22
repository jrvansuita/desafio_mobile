package com.example.demobycoders.di

import com.example.demobycoders.ui.home.HomeViewModel
import com.example.demobycoders.ui.login.LoginViewModel
import com.example.demobycoders.ui.splash.SplashViewModel
import com.example.demobycoders.usecase.CurrentUserUseCase
import com.example.demobycoders.usecase.LoginUseCase
import com.example.demobycoders.usecase.LogoutUseCase
import com.example.demobycoders.usecase.ReturningUserUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val appModule = module {
	viewModelOf(::SplashViewModel)
	viewModelOf(::LoginViewModel)
	viewModelOf(::HomeViewModel)
	
	factoryOf(::LoginUseCase)
	factoryOf(::LogoutUseCase)
	factoryOf(::ReturningUserUseCase)
	factoryOf(::CurrentUserUseCase)
}