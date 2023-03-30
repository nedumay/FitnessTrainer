package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.Repository
import javax.inject.Inject

class GetUserFromFirebase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(email: String) = repository.getUserFromFirebase(email)
}