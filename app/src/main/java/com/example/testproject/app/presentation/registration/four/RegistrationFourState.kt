package com.example.testproject.app.presentation.registration.four

import com.example.testproject.app.domain.model.User

data class RegistrationFourState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)