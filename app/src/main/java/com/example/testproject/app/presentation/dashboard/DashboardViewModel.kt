package com.example.testproject.app.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.usecase.firebase.AuthUserFirebase
import com.example.testproject.app.domain.usecase.notification.GetNotificationListUseCase
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val authUserFirebase: AuthUserFirebase,
    private val getNotificationListUseCase: GetNotificationListUseCase
) : ViewModel() {

    private var _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser>
        get() = _firebaseUser

    private var _notificationList = MutableLiveData<List<NotificationDashboard>>()
    val notificationList: LiveData<List<NotificationDashboard>>
        get() = _notificationList

    init {
        viewModelScope.launch {
            _firebaseUser.value = authUserFirebase.invoke()
            _notificationList.value = getNotificationListUseCase.invoke(_firebaseUser.value!!.uid)
        }
    }


}