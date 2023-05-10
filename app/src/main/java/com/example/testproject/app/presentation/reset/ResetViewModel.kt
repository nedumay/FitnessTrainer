package com.example.testproject.app.presentation.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.usecase.ResetPasswordUserFirebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResetViewModel @Inject constructor(
    private val resetPasswordUserFirebase: ResetPasswordUserFirebase
) : ViewModel() {

    private var _error = MutableStateFlow<Resource<String>>(Resource.Loading)
    val error = _error.asStateFlow()


    fun resetPassword(email: String) {
        viewModelScope.launch {
            try {
                _error.value = Resource.Loading
                delay(5000)
                val error = resetPasswordUserFirebase.invoke(email)
                _error.value = Resource.Success(error)
            } catch (e: Exception) {
                _error.value = Resource.Error(e.message.toString())
            }
        }
    }
}