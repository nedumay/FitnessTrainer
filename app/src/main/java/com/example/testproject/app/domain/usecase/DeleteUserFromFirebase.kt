package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.Repository
import javax.inject.Inject

class DeleteUserFromFirebase @Inject constructor(private val repository: Repository) {

    operator fun invoke(currentId: String) = repository.deleteUserFromFirebase(currentId)
}