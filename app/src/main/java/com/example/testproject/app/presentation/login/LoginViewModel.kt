package com.example.testproject.app.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.domain.usecase.LoginUserToFirebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUserToFirebase: LoginUserToFirebase
) : ViewModel() {

    private var _firebaseUser = MutableLiveData<String>()
    val firebaseUser: LiveData<String>
        get() = _firebaseUser

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    // Изменить получение ошибки!
    fun login(email: String, password: String){
        viewModelScope.launch {
            val userId = loginUserToFirebase.invoke(email = email, password = password)
            if(userId.isNotEmpty()){
                _firebaseUser.value = userId
            } else{
                _error.value = "Error"
            }
        }
    }
}