package com.example.testproject.app.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testproject.app.data.mapper.Mapper
import com.example.testproject.app.data.model.UserDbModel
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val mapper: Mapper
) : Repository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val dataBase: FirebaseDatabase = FirebaseDatabase.getInstance(
        "https://fitness-trainer-c88fe-default-rtdb.europe-west1.firebasedatabase.app/"
    )
    private val usersReference: DatabaseReference = dataBase.getReference("Users")

    private var _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser>
        get() = _user

    override fun addUserToFirebase(user: User): String {
        var error = "An account with this email already exists!"
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                if (it.user != null) {
                    val userDb: UserDbModel = mapper.mapEntityToDbModel(user, it.user!!.uid)
                    usersReference.child(userDb.id).setValue(userDb)
                    error = "The account was created successfully!"
                }
            }
            .addOnFailureListener {
                error = it.message.toString()
            }
        return error
    }

    override fun deleteUserFromFirebase(currentId: String) {
        TODO("Not yet implemented")
    }

    override fun getUserFromFirebase(currentId: String): User {
        /*
        auth.addAuthStateListener(object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                if(firebaseAuth.currentUser != null){
                    _user.value = firebaseAuth.currentUser
                }
            }

        })

        usersReference.child("Users").child(email).get().addOnSuccessListener {
            val data = mapper.mapDbModelToEntity(it.getValue(UserDbModel::class.java))
        }
        return
         */
        TODO()
    }

    override fun loginUserToFirebase(email: String, password: String): String {
        var userId : String? = null
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
               userId = it.user?.uid
        }
            .addOnFailureListener {
                Log.d("ErrorLogin", it.message.toString())
            }
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


}