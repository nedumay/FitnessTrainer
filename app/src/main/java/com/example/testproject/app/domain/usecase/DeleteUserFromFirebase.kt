package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.Repository
import javax.inject.Inject

class DeleteUserFromFirebase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(email: String) = repository.deleteUserFromFirebase(email)
}