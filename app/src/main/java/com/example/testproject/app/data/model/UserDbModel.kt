package com.example.testproject.app.data.model

data class UserDbModel(
    val id: String,
    val name: String,
    var lastName: String? = null,
    val gender: Boolean,
    val dateOfBirth: String,
    val height: String,
    val weight: String,
    val targetWeight: String,
    val email: String,
    val password: String,
    var country: String? = null,
    var city: String? = null
)