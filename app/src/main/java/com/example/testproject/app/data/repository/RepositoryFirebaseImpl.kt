package com.example.testproject.app.data.repository

import android.util.Log
import com.example.testproject.app.data.mapper.MapperUser
import com.example.testproject.app.data.database.model.UserDbModel
import com.example.testproject.app.domain.model.user.User
import com.example.testproject.app.domain.repository.RepositoryFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Repository Impl for work with Firebase.
 */
class RepositoryFirebaseImpl @Inject constructor(
    private val mapperUser: MapperUser
) : RepositoryFirebase {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance(
        "https://fitness-trainer-c88fe-default-rtdb.europe-west1.firebasedatabase.app/"
    )
    private val usersReference: DatabaseReference = dataBase.getReference("Users")

    //Add user to Firebase.
    override suspend fun addUserToFirebase(user: User): String {
        var error = "An account with this email already exists!"
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                if (it.user != null) {
                    val userDb: UserDbModel = mapperUser.mapEntityToDbModel(user, it.user!!.uid)
                    usersReference.child(userDb.id!!).setValue(userDb)
                    error = "The account was created successfully!"
                }
            }
            .addOnFailureListener {
                error = it.message.toString()
            }.await()
        return error
    }
    //Delete user from Firebase.
    override fun deleteUserFromFirebase(id: String) {
        auth.currentUser?.delete()?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Delete account", "Deletion success account from Firebase")
            }
        }
        usersReference.child(id).removeValue()
        Log.d("Delete account", "Delete data about user")
    }

    //Get user from Firebase. Refactored!
    override suspend fun getUserFromFirebase(id: String): User? {
        var count = 0
        var data: User? = null
        Log.d("Dashboard account user", "rep.impl $id")
        usersReference.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data = mapperUser.mapDbModelToEntity(snapshot.getValue(UserDbModel::class.java))
                Log.d("Dashboard account user", "rep.impl data: $data")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Dashboard account user", "rep.impl error: $error")
            }
        })
        while (data == null){
            delay(1000)
            count++
            if(count>15){
                break
            }
        }
        Log.d("Dashboard account user", "rep.impl return $data")
        return data
    }
    //Login user to Firebase with email and password. Refactored!
    override suspend fun loginUserToFirebase(email: String, password: String): String {
        var userId = ""
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userId = it.result.user?.uid ?: ""
                } else {
                    userId = it.exception?.message.toString()
                    Log.d("ErrorLogin", it.exception?.message.toString())
                }
            }.await()
        return userId
    }
    //Reset password user to Firebase.
    override suspend fun resetPasswordUserToFirebase(email: String): String {
        var error = ""
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                error = "Link to reset password sent to your email!"
            }
            .addOnFailureListener {
                error = it.message.toString()
            }.await()
        return error
    }
    //Sign out user from Firebase.
    override fun signOutUserFromFirebase(): FirebaseUser? {
        auth.signOut()
        Log.d("Account user", "rep.impl ${auth.currentUser}")
        return auth.currentUser
    }
    //Auth user from Firebase.
    override fun authUserFirebase(): FirebaseUser? {
        val user: FirebaseUser?  = auth.currentUser
        Log.d("Account user", "rep.impl $user")
        return auth.currentUser
    }

}