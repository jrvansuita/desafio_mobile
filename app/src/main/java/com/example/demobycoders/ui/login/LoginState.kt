package com.example.demobycoders.ui.login

import android.os.Parcelable
import com.example.toolui.state.ButtonState
import com.example.toolui.state.InputState
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginState(
	val email: InputState = InputState(),
	val password: InputState = InputState(),
	val submit: ButtonState = ButtonState()
) : Parcelable

sealed class LoginAction {
	object Success : LoginAction()
	data class Failure(val throwable: Throwable) : LoginAction()
}