package com.example.testproject.app.presentation.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testproject.app.domain.usecase.GetUserFromFirebase
import javax.inject.Inject

class ResetViewModel @Inject constructor(
    private val getUserFromFirebase: GetUserFromFirebase
) : ViewModel() {

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