package com.example.demobycoders.usecase

import com.example.toolthirdparty.auth.AuthWrapper

class ReturningUserUseCase(
	private val authWrapper: AuthWrapper
) {
	operator fun invoke() = authWrapper.isLogged()
}