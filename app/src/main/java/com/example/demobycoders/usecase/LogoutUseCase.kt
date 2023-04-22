package com.example.demobycoders.usecase

import com.example.tooldata.datasource.local.store.LocalStore
import com.example.tooldata.model.User
import com.example.toolthirdparty.auth.AuthWrapper

class LogoutUseCase(
	private val localStore: LocalStore<User>,
	private val authWrapper: AuthWrapper
) {
	suspend operator fun invoke() {
		localStore.update {
			it.copy(
				lat = .0,
				long = .0
			)
		}
		authWrapper.logout()
	}
}
