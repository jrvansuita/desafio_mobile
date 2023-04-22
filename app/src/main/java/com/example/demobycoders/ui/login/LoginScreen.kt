package com.example.demobycoders.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.demobycoders.R
import com.example.demobycoders.extensions.collectWithLifecycle
import com.example.demobycoders.ui.theme.DemoByCodersTheme
import com.example.toolui.component.Button
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
private fun Preview() {
	DemoByCodersTheme {
		LoginComposable(state = LoginState())
	}
}

@Composable
fun LoginScreen(
	viewModel: LoginViewModel = koinViewModel(),
	onNavigateToHome: (() -> Unit)? = null
) {
	val context = LocalContext.current
	val state by viewModel.stateFlow.collectAsStateWithLifecycle()

	LoginComposable(state, viewModel::onEmailChange, viewModel::onPasswordChange) {
		viewModel.onSubmit()
	}

	viewModel.action.collectWithLifecycle {
		when (it) {
			is LoginAction.Failure -> {
				Toast.makeText(context, it.throwable.message, Toast.LENGTH_LONG).show()
			}
			LoginAction.Success -> onNavigateToHome?.invoke()
		}
	}
}

@Composable
fun LoginComposable(
	state: LoginState,
	onEmailChange: ((String) -> Unit)? = null,
	onPasswordChange: ((String) -> Unit)? = null,
	onSubmit: (() -> Unit)? = null
) = Column(
	modifier = Modifier.fillMaxSize(),
	horizontalAlignment = Alignment.CenterHorizontally,
	verticalArrangement = Arrangement.SpaceAround,
) {
	Column(
		modifier = Modifier.fillMaxWidth(.8f),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		CreateTitleText()
		CreateEmailField(state, onEmailChange)
		CreatePasswordField(state, onPasswordChange, onSubmit)
		CreateSubmitButton(onSubmit, state)
	}
}

@Composable
private fun CreateTitleText() = Text(text = stringResource(R.string.login_title))

@Composable
private fun CreateEmailField(
	state: LoginState,
	onEmailChange: ((String) -> Unit)?
) = OutlinedTextField(
	modifier = Modifier
		.fillMaxWidth()
		.padding(top = 32.dp, bottom = 16.dp),
	value = state.email.value,
	enabled = state.email.enabled,
	singleLine = true,
	keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
	label = { Text(text = stringResource(R.string.email)) },
	placeholder = { Text(text = stringResource(R.string.email_example)) },
	onValueChange = { onEmailChange?.invoke(it) }
)

@Composable
private fun CreatePasswordField(
	state: LoginState,
	onPasswordChange: ((String) -> Unit)?,
	onDone: (() -> Unit)?,
	focusManager: FocusManager = LocalFocusManager.current
) = OutlinedTextField(
	visualTransformation = PasswordVisualTransformation(),
	modifier = Modifier
		.fillMaxWidth()
		.padding(bottom = 16.dp),
	value = state.password.value,
	singleLine = true,
	keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
	keyboardActions = KeyboardActions(onDone = {
		focusManager.clearFocus()
		onDone?.invoke()
	}),
	enabled = state.password.enabled,
	label = { Text(text = stringResource(R.string.password)) },
	placeholder = { Text(text = stringResource(R.string.password_placeholder)) },
	onValueChange = { onPasswordChange?.invoke(it) }
)

@Composable
private fun CreateSubmitButton(
	onSubmit: (() -> Unit)?,
	state: LoginState
) = Button(
	modifier = Modifier.fillMaxWidth(),
	text = stringResource(R.string.login),
	onClick = { onSubmit?.invoke() },
	enabled = state.submit.enabled,
	loading = state.submit.loading
)