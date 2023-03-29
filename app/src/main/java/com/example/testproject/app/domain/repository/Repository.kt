package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.User

interface Repository {

    suspend fun addUserToFirebase(user: User)

    suspend fun deleteUserFromFirebase(email: String)

    suspend fun getUserFromFirebase(email: String)
}