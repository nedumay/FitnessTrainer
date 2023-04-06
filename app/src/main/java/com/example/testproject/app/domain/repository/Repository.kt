package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.User
import com.google.firebase.auth.FirebaseUser

interface Repository {

    fun addUserToFirebase(user: User) : String

    fun deleteUserFromFirebase(currentId: String)

    fun getUserFromFirebase(currentId: String) : User

    fun loginUserToFirebase(email: String, password: String) : String
}