package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.User
import com.google.firebase.auth.FirebaseUser

interface Repository {

    fun addUserToFirebase(user: User) : String

    fun deleteUserFromFirebase(id: String)

    suspend fun getUserFromFirebase(id: String) : User?

    suspend fun loginUserToFirebase(email: String, password: String) : String

    fun resetPasswordUserToFirebase(email: String) : String

    fun signOutUserFromFirebase() : FirebaseUser?

    fun authUserFirebase() : FirebaseUser?
}