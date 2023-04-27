package com.example.testproject.app.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.usecase.DeleteUserFromFirebase
import com.example.testproject.app.domain.usecase.GetUserFromFirebase
import com.example.testproject.app.domain.usecase.SignOutUserFromFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getUserFromFirebase: GetUserFromFirebase,
    private val signOutUserFromFirebase: SignOutUserFromFirebase,
    private val deleteUserFromFirebase: DeleteUserFromFirebase
) : ViewModel() {

    private var _userInfo = MutableStateFlow<Resource<User>>(Resource.Loading)
    val userInfo = _userInfo.asStateFlow()

    fun loadDataForUser(currentId: String) {
        viewModelScope.launch {
            try {
                _userInfo.value = Resource.Loading
                val data = getUserFromFirebase.invoke(currentId)
                _userInfo.value = Resource.Success(data!!)
            } catch (e: Exception) {
                _userInfo.value = Resource.Error(e.message.toString())
            }
            Log.d("Settings activity", "Settings viewModel: ${_userInfo.value}")
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                signOutUserFromFirebase.invoke()
                _userInfo.value = Resource.Loading
            } catch (e: Exception) {
                _userInfo.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun deleteUser(currentId: String) {
        viewModelScope.launch {
            try {
                deleteUserFromFirebase.invoke(currentId)
                signOutUserFromFirebase.invoke()
                _userInfo.value = Resource.Loading
            } catch (e: Exception) {
                _userInfo.value = Resource.Error(e.message.toString())
            }
        }
    }
}