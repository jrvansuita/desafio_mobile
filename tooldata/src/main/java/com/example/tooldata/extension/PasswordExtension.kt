package com.example.tooldata.extension

import com.example.tooldata.model.Password

fun Password.isValid() = value.length >= 6