package com.example.toolthirdparty.exception

import com.google.firebase.auth.FirebaseUser

class FirebaseUserNotFoundException : Exception() {

	companion object {
		fun throwing(user: FirebaseUser?) {
			if (user == null) {
				throw FirebaseUserNotFoundException()
			}
		}
	}
}