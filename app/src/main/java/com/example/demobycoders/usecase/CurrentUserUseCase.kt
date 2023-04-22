package com.example.demobycoders.usecase

import com.example.toolthirdparty.auth.AuthWrapper

class CurrentUserUseCase(
	private val authWrapper: AuthWrapper
) {
	operator fun invoke() = authWrapper.getCurrentUser()
}