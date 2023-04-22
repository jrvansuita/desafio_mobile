package com.example.demobycoders.usecase

import com.example.tooldata.datasource.local.store.LocalStore
import com.example.tooldata.model.User
import com.example.toolthirdparty.analytics.AnalyticsWrapper
import com.example.toolthirdparty.auth.AuthWrapper


class LoginUseCase(
	private val localStore: LocalStore<User>,
	private val authWrapper: AuthWrapper,
	private val analyticsWrapper: AnalyticsWrapper
) {
	suspend operator fun invoke(email: String, password: String): Result<User> =
		authWrapper.login(email, password).fold(onSuccess = { user ->
			localStore.setData(user)
			analyticsWrapper.login(email)
			Result.success(user)
		}, onFailure = {
			Result.failure(it)
		})

}
