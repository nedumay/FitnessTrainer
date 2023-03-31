package com.example.testproject.app.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testproject.app.data.mapper.Mapper
import com.example.testproject.app.data.model.UserDbModel
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val mapper: Mapper
) : Repository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val usersReference: DatabaseReference = dataBase.getReference("Users")

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    override suspend fun addUserToFirebase(user: User) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                if (it.user != null) {
                    val userDb: UserDbModel = mapper.mapEntityToDbModel(user, it.user!!.uid)
                    usersReference.child(userDb.id).setValue(userDb)
                }
            }
            .addOnFailureListener {
                Log.d("addUser", it.toString())
            }
    }

    override suspend fun deleteUserFromFirebase(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserFromFirebase(email: String): User {
        /*
        usersReference.child("Users").child(email).get().addOnSuccessListener {
            val data = mapper.mapDbModelToEntity(it.getValue(UserDbModel::class.java))
        }
        return
         */
        TODO()
    }


}