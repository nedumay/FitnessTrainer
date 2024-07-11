package com.example.testproject.app.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.usecase.firebase.LoginUserToFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUserToFirebase: LoginUserToFirebase
) : ViewModel() {

    private var _firebaseUser = MutableStateFlow<Resource<String>>(Resource.Loading)
    val firebaseUser = _firebaseUser.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _firebaseUser.value = Resource.Loading
                val userId = loginUserToFirebase.invoke(email = email, password = password)
                Log.d("LoginActivityData", "userId: ${userId}")
                if (userId.isNotEmpty()) {
                    _firebaseUser.value = Resource.Success(userId)
                } else {
                    _firebaseUser.value =
                        Resource.Error("The account with this e-mail does not exist!")
                }
            } catch (e: Exception) {
                _firebaseUser.value = Resource.Error(e.message.toString())
            }

        }
    }
}