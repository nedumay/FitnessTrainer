package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.User

interface Repository {

    fun addUserToFirebase(user: User) : String

    fun deleteUserFromFirebase(email: String)

    fun getUserFromFirebase(email: String) : User
}