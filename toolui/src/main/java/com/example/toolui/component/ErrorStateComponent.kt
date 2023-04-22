package com.example.toolui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demobycoders.ui.theme.DemoByCodersTheme
import com.example.toolui.R
import com.example.toolui.state.ButtonState

@Preview
@Composable
private fun Preview() {
	DemoByCodersTheme {
		ErrorStateComponent(message = "Teste", onClick = {})
	}
}

@Composable
fun ErrorStateComponent(
	title: String? = null,
	message: String? = null,
	buttonState: ButtonState = ButtonState(),
	onClick: (() -> Unit)? = null,
) {
	Column(
		modifier = Modifier
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Column(
			modifier = Modifier
				.weight(1f)
				.fillMaxWidth()
				.padding(32.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Text(text = title ?: stringResource(R.string.something_went_wrong), fontSize = 18.sp)
			Spacer(modifier = Modifier.padding(8.dp))
			message?.let { Text(text = it) }
			Spacer(modifier = Modifier.padding(16.dp))
			onClick?.let {
				Button(
					text = stringResource(R.string.try_again),
					onClick = onClick,
					loading = buttonState.loading,
					enabled = buttonState.enabled
				)
			}
		}
	}
}