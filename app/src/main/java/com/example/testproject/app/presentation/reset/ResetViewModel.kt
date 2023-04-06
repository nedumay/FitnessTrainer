package com.example.testproject.app.presentation.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testproject.app.domain.usecase.GetUserFromFirebase
import com.example.testproject.app.domain.usecase.ResetPasswordUserFirebase
import javax.inject.Inject

class ResetViewModel @Inject constructor(
    private val resetPasswordUserFirebase: ResetPasswordUserFirebase
) : ViewModel() {

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    fun resetPassord(email: String){
        _error.value = resetPasswordUserFirebase.invoke(email)
    }
}