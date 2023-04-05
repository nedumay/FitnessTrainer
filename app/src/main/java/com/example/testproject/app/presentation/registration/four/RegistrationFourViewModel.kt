package com.example.testproject.app.presentation.registration.four

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.usecase.AddUserToFirebase
import com.example.testproject.app.domain.usecase.GetUserFromFirebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegistrationFourViewModel @Inject constructor(
    private val addUserToFirebase: AddUserToFirebase,
) : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
    get() = _error

    fun signUp(
        email: String,
        password: String,
        name: String,
        date: String,
        gender: Boolean,
        height: String,
        weight: String,
        targetWeight: String
    ) {
        val userAdd = User(
            email = email,
            password = password,
            name = name,
            dateOfBirth = date,
            gender = gender,
            height = height,
            weight = weight,
            targetWeight = targetWeight
        )
        _error.value = addUserToFirebase.invoke(userAdd)
    }

}