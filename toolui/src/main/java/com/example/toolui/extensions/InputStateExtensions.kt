package com.example.toolui.extensions

import com.example.toolui.state.InputState

fun InputState.isCompleted() = value.isNotBlank() && valid

