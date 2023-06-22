package com.example.testproject.app.presentation.registration.four

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.user.User
import com.example.testproject.app.domain.usecase.firebase.AddUserToFirebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationFourViewModel @Inject constructor(
    private val addUserToFirebase: AddUserToFirebase
) : ViewModel() {

    private var _error = MutableStateFlow<Resource<String>>(Resource.Loading)
    val error = _error.asStateFlow()

    fun signUp(
        email: String,
        password: String,
        name: String,
        lastName: String,
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
            lastName = lastName,
            dateOfBirth = date,
            gender = gender,
            height = height,
            weight = weight,
            targetWeight = targetWeight
        )
        viewModelScope.launch {
            try {
                _error.value = Resource.Loading
                val error = addUserToFirebase.invoke(userAdd)
                _error.value = Resource.Success(error)
            } catch (e: Exception) {
                _error.value = Resource.Error(e.toString())
            }
        }
    }

}