package com.example.toolui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Button(
	modifier: Modifier = Modifier,
	text: String,
	onClick: () -> Unit,
	enabled: Boolean = true,
	loading: Boolean = false,
	shape: Shape = RoundedCornerShape(4.dp),
) = androidx.compose.material3.Button(
	modifier = modifier,
	shape = shape,
	onClick = {
		if (!loading && enabled) {
			onClick()
		}
	},
	enabled = enabled,
) {
	if (loading) {
		CircularProgressIndicator(modifier = Modifier.size(16.dp))
	} else {
		Text(
			text = text,
			fontSize = 15.sp,
		)
	}
}
