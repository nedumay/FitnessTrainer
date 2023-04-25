package com.example.testproject.app.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.domain.usecase.AuthUserFirebase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val authUserFirebase: AuthUserFirebase
) : ViewModel() {

    private var _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser>
        get() = _firebaseUser

    init {
        viewModelScope.launch {
            _firebaseUser.value = authUserFirebase.invoke()
        }
    }

}