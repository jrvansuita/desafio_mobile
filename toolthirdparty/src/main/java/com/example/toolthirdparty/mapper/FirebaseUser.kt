package com.example.toolthirdparty.mapper

import com.example.tooldata.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUser() = User(this.email.orEmpty())