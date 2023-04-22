package com.example.toolthirdparty.auth

import com.example.tooldata.model.User
import com.example.toolthirdparty.exception.FirebaseUserNotFoundException
import com.example.toolthirdparty.mapper.toUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class AuthWrapperImpl : AuthWrapper {

	private val firebaseAuth by lazy { Firebase.auth }

	override suspend fun login(email: String, password: String) =
		suspendCoroutine<Result<User>> { continuation ->
			firebaseAuth.signInWithEmailAndPassword(email, password)
				.addOnSuccessListener {
					continuation.resume(Result.success(it.user!!.toUser()))
				}.addOnFailureListener {
					continuation.resume(Result.failure(it))
				}
		}

	override fun logout() = firebaseAuth.signOut()

	override fun isLogged() = firebaseAuth.currentUser != null

	override fun getCurrentUser() = with(firebaseAuth.currentUser) {
		FirebaseUserNotFoundException.throwing(this)
		this?.toUser()
	}


}