package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.Repository
import javax.inject.Inject

class ResetPasswordUserFirebase @Inject constructor(private val repository: Repository) {

    operator suspend fun invoke(email: String) = repository.resetPasswordUserToFirebase(email)
}