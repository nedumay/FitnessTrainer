package com.example.testproject.app.data.repository

import android.util.Log
import com.example.testproject.app.data.mapper.Mapper
import com.example.testproject.app.data.model.UserDbModel
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.repository.Repository
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

class RepositoryImpl @Inject constructor(
    private val mapper: Mapper
) : Repository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance(
        "https://fitness-trainer-c88fe-default-rtdb.europe-west1.firebasedatabase.app/"
    )
    private val usersReference: DatabaseReference = dataBase.getReference("Users")

    override suspend fun addUserToFirebase(user: User): String {
        var error = "An account with this email already exists!"
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                if (it.user != null) {
                    val userDb: UserDbModel = mapper.mapEntityToDbModel(user, it.user!!.uid)
                    usersReference.child(userDb.id!!).setValue(userDb)
                    error = "The account was created successfully!"
                }
            }
            .addOnFailureListener {
                error = it.message.toString()
            }.await()
        return error
    }

    override fun deleteUserFromFirebase(id: String) {
        auth.currentUser?.delete()?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Delete account", "Deletion success account from Firebase")
            }
        }
        usersReference.child(id).removeValue()
        Log.d("Delete account", "Delete data about user")
    }

    //Получение данных о пользователе. Исправить!
    override suspend fun getUserFromFirebase(id: String): User? {
        var count = 0
        var data: User? = null
        Log.d("Dashboard account user", "rep.impl $id")
        usersReference.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data = mapper.mapDbModelToEntity(snapshot.getValue(UserDbModel::class.java))
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

    override suspend fun loginUserToFirebase(email: String, password: String): String {
        var userId: String? = null
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userId = it.result.user?.uid
                } else {
                    Log.d("ErrorLogin", it.exception?.message.toString())
                }
            }.await()
        return userId.toString()
    }

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

    override fun signOutUserFromFirebase(): FirebaseUser? {
        auth.signOut()
        Log.d("Account user", "rep.impl ${auth.currentUser}")
        return auth.currentUser
    }

    override fun authUserFirebase(): FirebaseUser? {
        val user: FirebaseUser?  = auth.currentUser
        Log.d("Account user", "rep.impl ${user}")
        return auth.currentUser
    }


}