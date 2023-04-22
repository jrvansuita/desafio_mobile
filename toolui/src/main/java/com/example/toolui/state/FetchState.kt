package com.example.toolui.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
sealed class FetchState<out T> : Parcelable {
	object Loading : FetchState<Nothing>()
	data class Error<T>(val error: Throwable) : FetchState<T>()
	data class Success<T>(val data: @RawValue T) : FetchState<T>()
}

fun <T> Result<T>.toFetchState() = fold(
	onSuccess = {
		FetchState.Success(it)
	},
	onFailure = {
		FetchState.Error(it)
	}
)

