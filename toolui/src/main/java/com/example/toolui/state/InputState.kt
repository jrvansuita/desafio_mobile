package com.example.toolui.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class InputState(
	val value: String = "",
	val enabled: Boolean = true,
	val valid: Boolean = true,
) : Parcelable

