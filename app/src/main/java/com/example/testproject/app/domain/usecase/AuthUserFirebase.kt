package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.Repository
import javax.inject.Inject

class AuthUserFirebase @Inject constructor(private val repository: Repository) {

    operator fun invoke() = repository.authUserFirebase()
}