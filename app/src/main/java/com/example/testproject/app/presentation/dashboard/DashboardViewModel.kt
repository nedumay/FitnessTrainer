package com.example.testproject.app.presentation.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.usecase.GetUserFromFirebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getUserFromFirebase: GetUserFromFirebase
) : ViewModel() {

    private var _firebaseUser = MutableLiveData<User>()
    val firebaseUser: LiveData<User>
        get() = _firebaseUser

    fun loadDataForUser(currentId: String) {
        viewModelScope.launch {
            _firebaseUser.value = getUserFromFirebase.invoke(currentId)
            Log.d("Dashboard account user", "User viewModel: ${_firebaseUser.value}")
        }
    }
}