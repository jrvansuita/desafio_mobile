package com.example.toolthirdparty.auth

import com.example.tooldata.model.User

interface AuthWrapper {
	suspend fun login(email: String, password: String): Result<User>
	fun logout()
	fun isLogged(): Boolean
	fun getCurrentUser(): User?
}