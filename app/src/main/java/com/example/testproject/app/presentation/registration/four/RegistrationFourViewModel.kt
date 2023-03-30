package com.example.testproject.app.presentation.registration.four

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.usecase.AddUserToFirebase
import com.example.testproject.app.domain.usecase.GetUserFromFirebase
import javax.inject.Inject

class RegistrationFourViewModel @Inject constructor(
    private val addUserToFirebase: AddUserToFirebase,
    private val getUserFromFirebase: GetUserFromFirebase
) : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
    get() = _error

    fun getUser(email: String) {
        TODO("Get Firebase user")
    }

    fun addUser(email: String, password: String) {
        TODO("Add Firebase user")
    }

}