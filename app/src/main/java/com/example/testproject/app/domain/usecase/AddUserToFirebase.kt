package com.example.testproject.app.domain.usecase

import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.repository.Repository
import javax.inject.Inject

class AddUserToFirebase @Inject constructor(private val repository: Repository) {
    operator fun invoke(user: User) = repository.addUserToFirebase(user = user)

}