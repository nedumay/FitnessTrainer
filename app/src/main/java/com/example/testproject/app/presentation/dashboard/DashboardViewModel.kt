package com.example.testproject.app.presentation.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.usecase.firebase.AuthUserFirebase
import com.example.testproject.app.domain.usecase.notification.GetNotificationListUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val authUserFirebase: AuthUserFirebase,
    private val getNotificationListUseCase: GetNotificationListUseCase
) : ViewModel() {

    private var _firebaseUser = MutableStateFlow<Resource<FirebaseUser?>>(Resource.Loading)
    val firebaseUser = _firebaseUser.asStateFlow()

    private var _notificationList = MutableLiveData<List<NotificationDashboard>>()
    val notificationList: LiveData<List<NotificationDashboard>>
        get() = _notificationList

    init {
        viewModelScope.launch {
            try {
                _firebaseUser.value = Resource.Loading
                val data = authUserFirebase.invoke()
                _firebaseUser.value = Resource.Success(data)
                val uid = data.uid ?: ""
                if(uid.isNotEmpty()) {
                    _notificationList.value = getNotificationListUseCase.invoke(uid)
                }
            } catch (e: Exception) {
                Log.d("DashboardViewModelError", e.toString())
            }
        }
    }
}