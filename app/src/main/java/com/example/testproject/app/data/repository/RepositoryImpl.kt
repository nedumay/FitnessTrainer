package com.example.testproject.app.data.repository

import com.example.testproject.app.data.model.UserDbModel
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.repository.Repository

class RepositoryImpl : Repository {

    override suspend fun addUserToFirebase(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserFromFirebase(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserFromFirebase(email: String): UserDbModel {
        TODO("Not yet implemented")
    }


}