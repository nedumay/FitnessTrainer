package com.example.testproject.app.domain.model.user

import androidx.annotation.Keep

@Keep
data class User(
    var id: String? = "",
    var name: String,
    var lastName: String,
    var gender: Boolean,
    var dateOfBirth: String,
    var height: String,
    var weight: String,
    var targetWeight: String,
    var email: String,
)
