package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.Repository
import javax.inject.Inject

class LoginUserToFirebase @Inject constructor(private val repository: Repository) {

    operator suspend fun invoke(email: String, password: String) =
        repository.loginUserToFirebase(email, password)
}