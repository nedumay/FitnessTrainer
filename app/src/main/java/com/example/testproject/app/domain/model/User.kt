package com.example.testproject.app.domain.model

data class User(
    val id: String,
    val name: String,
    val lastName: String? = null,
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
