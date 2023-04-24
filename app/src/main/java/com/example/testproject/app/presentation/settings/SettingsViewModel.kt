package com.example.testproject.app.presentation.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.domain.model.User
import com.example.testproject.app.domain.usecase.DeleteUserFromFirebase
import com.example.testproject.app.domain.usecase.GetUserFromFirebase
import com.example.testproject.app.domain.usecase.SignOutUserFromFirebase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getUserFromFirebase: GetUserFromFirebase,
    private val signOutUserFromFirebase: SignOutUserFromFirebase,
    private val deleteUserFromFirebase: DeleteUserFromFirebase
) : ViewModel() {

    private var _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User>
        get() = _userInfo

    private var _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser>
        get() = _firebaseUser

    fun loadDataForUser(currentId: String) {
        viewModelScope.launch {
            _userInfo.value = getUserFromFirebase.invoke(currentId)
            Log.d("Settings activity", "Settings viewModel: ${_userInfo.value}")
        }
    }
    // Получить данный id пользователя и сохранить в _firebaseUser. Если он null -> все ок выходим из аккаунта на страницу логина.
    fun signOut() {
        _firebaseUser.value = signOutUserFromFirebase.invoke()
        _userInfo.value = null
        _firebaseUser.value = null
    }

    fun deleteUser(currentId: String){
        _firebaseUser.value = signOutUserFromFirebase.invoke()
        deleteUserFromFirebase.invoke(currentId)
        _userInfo.value = null
        _firebaseUser.value = null

    }
}