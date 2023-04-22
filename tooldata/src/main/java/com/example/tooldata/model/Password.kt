package com.example.tooldata.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Password(
	val value: String = "",
) : Parcelable