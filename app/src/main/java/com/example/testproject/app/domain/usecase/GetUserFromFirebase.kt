package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.repository.Repository
import javax.inject.Inject

class GetUserFromFirebase @Inject constructor(private val repository: Repository) {

    operator suspend fun invoke(currentId: String) = repository.getUserFromFirebase(currentId)
}