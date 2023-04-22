package com.example.tooldata.extension

import androidx.core.util.PatternsCompat
import com.example.tooldata.model.Email

fun Email.isValid() =
	value.isNotBlank() && PatternsCompat.EMAIL_ADDRESS.matcher(value).matches()