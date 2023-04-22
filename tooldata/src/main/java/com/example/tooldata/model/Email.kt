package com.example.tooldata.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Email(val value: String = "") : Parcelable