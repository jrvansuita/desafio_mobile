package com.example.toolui.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ButtonState(
	val loading: Boolean = false,
	val enabled: Boolean = !loading
) : Parcelable