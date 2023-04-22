package com.example.tooldata.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class User(
	val email: String = "",
	val lat: Double = .0,
	val long: Double = .0
) : Parcelable