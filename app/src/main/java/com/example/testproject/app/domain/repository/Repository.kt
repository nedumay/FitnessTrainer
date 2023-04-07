package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.User

interface Repository {

    fun addUserToFirebase(user: User) : String

    fun deleteUserFromFirebase(currentId: String)

    suspend fun getUserFromFirebase(id: String) : User?

    suspend fun loginUserToFirebase(email: String, password: String) : String

    fun resetPasswordUserToFirebase(email: String) : String
}