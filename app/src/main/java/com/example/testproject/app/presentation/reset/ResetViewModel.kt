package com.example.testproject.app.presentation.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResetViewModel : ViewModel() {

    private var _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    fun save(email: String){
        _email.value = email
    }

    fun resetPassord(email: String){
        TODO("Reset password")
    }
}