package com.example.demobycoders.ui.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.demobycoders.template.EventEmitterViewModel
import com.example.demobycoders.template.EventEmitterViewModelImpl
import com.example.demobycoders.template.SavedStateViewModel
import com.example.demobycoders.usecase.LoginUseCase
import com.example.tooldata.datasource.local.store.LocalStore
import com.example.tooldata.extension.isValid
import com.example.tooldata.model.Email
import com.example.tooldata.model.Password
import com.example.tooldata.model.User
import com.example.toolui.extensions.isCompleted
import com.example.toolui.state.ButtonState
import com.example.toolui.state.InputState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
	savedStateHandle: SavedStateHandle,
	private val localStore: LocalStore<User>,
	private val loginUseCase: LoginUseCase,
) : SavedStateViewModel<LoginState>(savedStateHandle),
	EventEmitterViewModel<LoginAction> by EventEmitterViewModelImpl() {

	override fun startsWith() = LoginState(submit = ButtonState(enabled = false))

	init {
		viewModelScope.launch {
			localStore.getData().collectLatest { user ->
				if (user.email.isNotBlank()) {
					update { it.copy(email = InputState(value = user.email)) }
				}
			}
		}
	}

	private fun isFormComplete() = with(state) {
		listOf(email, password).all {
			it.isCompleted()
		}
	}

	private fun updateAndCheck(action: (LoginState) -> LoginState) {
		update(action)
		update { it.copy(submit = ButtonState(enabled = isFormComplete())) }
	}

	fun onEmailChange(value: String) = updateAndCheck {
		it.copy(
			email = InputState(
				value = value.lowercase(),
				valid = Email(value).isValid()
			)
		)
	}

	fun onPasswordChange(value: String) = updateAndCheck {
		it.copy(
			password = InputState(
				value = value.lowercase(),
				valid = Password(value).isValid()
			)
		)
	}

	private fun toggleLoading(loading: Boolean) = update {
		it.copy(submit = ButtonState(loading = loading))
	}

	fun onSubmit() {
		if (state.submit.enabled && isFormComplete())
			viewModelScope.launch {
				toggleLoading(true)
				loginUseCase(
					email = state.email.value,
					password = state.password.value
				).fold(onSuccess = {
					emit(LoginAction.Success)
				}, onFailure = {
					emit(LoginAction.Failure(it))
				})
				toggleLoading(false)
			}
	}

}
