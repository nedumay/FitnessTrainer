package com.example.testproject.app.data.repository

import android.util.Log
import com.example.testproject.app.data.mapper.Mapper
import com.example.testproject.app.data.model.UserDbModel
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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

    override fun addUserToFirebase(user: User): String {
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
            }
        return error
    }

    override fun deleteUserFromFirebase(id: String) {
        auth.currentUser?.delete()?.addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("Delete account", "Deletion success account from Firebase")
            }
        }
        usersReference.child(id).removeValue()
        Log.d("Delete account", "Delete data about user")
    }

    //Получение данных о пользователе. Исправить!
    override suspend fun getUserFromFirebase(id: String): User? {
        /*var user: FirebaseUser? = null
        auth.addAuthStateListener(object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                if (firebaseAuth.currentUser != null) {
                    Log.d("Dashboard account user", "rep.impl ${firebaseAuth.currentUser}")
                    user = firebaseAuth.currentUser
                }
            }
        })*/
        var data: User? = null
        var errorDb: DatabaseError? = null
        Log.d("Dashboard account user", "rep.impl $id")
        usersReference.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data = mapper.mapDbModelToEntity(snapshot.getValue(UserDbModel::class.java))
                Log.d("Dashboard account user", "rep.impl $data")
            }
            override fun onCancelled(error: DatabaseError) {
                errorDb = error
                Log.d("Dashboard account user", "rep.impl error: $error")
            }
        })
        while(data == null){
            delay(1000)
            if(errorDb != null){
                break
            }
        }
        Log.d("Dashboard account user", "rep.impl return $data")
        return data
    }

    //Изменить передачу ошибки
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

    override fun resetPasswordUserToFirebase(email: String): String {
        var error = ""
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                error = "Link to reset password sent to your email!"
            }
            .addOnFailureListener {
                error = it.message.toString()
            }
        return error
    }

    override fun signOutUserFromFirebase(): FirebaseUser? {
        auth.signOut()
        return auth.currentUser
    }


}