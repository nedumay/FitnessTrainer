package com.example.testproject.app.data.model

import com.example.testproject.app.domain.model.User

data class UserDbModel(
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

fun UserDbModel.toUser(): User {
    return User(
        id = id,
        name = name,
        lastName = lastName,
        gender = gender,
        dateOfBirth = dateOfBirth,
        height = height,
        weight = weight,
        targetWeight = targetWeight,
        email = email,
        password = password,
        country = country,
        city = city
    )
}