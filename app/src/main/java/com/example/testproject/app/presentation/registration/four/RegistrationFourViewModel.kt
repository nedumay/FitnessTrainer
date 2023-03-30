package com.example.testproject.app.presentation.registration.four

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.usecase.AddUserToFirebase
import com.example.testproject.app.domain.usecase.GetUserFromFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RegistrationFourViewModel @Inject constructor(
    private val addUserToFirebase: AddUserToFirebase,
    private val getUserFromFirebase: GetUserFromFirebase
) : ViewModel() {

    private var _state = MutableStateFlow(RegistrationFourState())
    val state: StateFlow<RegistrationFourState>
        get() = _state

    private fun getUser(email: String) {
        getUserFromFirebase(email).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RegistrationFourState(user = result.data)
                }
                is Resource.Error -> {
                    _state.value = RegistrationFourState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = RegistrationFourState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}