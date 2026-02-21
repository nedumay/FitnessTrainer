package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.user.User
import com.google.firebase.auth.FirebaseUser

/**
 * @author Nedumayy (Samim)
 * Repository interface for work with Firebase.
 */
interface RepositoryFirebase {

    //Add user to Firebase.
    suspend fun addUserToFirebase(user: User, password: String): String
    //Delete user from Firebase.
    fun deleteUserFromFirebase(id: String)
    //Get user from Firebase.
    suspend fun getUserFromFirebase(id: String): User?
    //Login user to Firebase with email and password.
    suspend fun loginUserToFirebase(email: String, password: String): String
    //Reset password user to Firebase.
    suspend fun resetPasswordUserToFirebase(email: String): String
    //Sign out user from Firebase.
    fun signOutUserFromFirebase(): FirebaseUser?
    //Auth user from Firebase.
    fun authUserFirebase(): FirebaseUser?
}