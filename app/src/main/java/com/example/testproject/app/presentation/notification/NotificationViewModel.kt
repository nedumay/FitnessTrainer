package com.example.testproject.app.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.usecase.notification.AddNotificationItemUseCase
import com.example.testproject.app.domain.usecase.notification.ClearAllNotificationFromDbUseCase
import com.example.testproject.app.domain.usecase.notification.GetNotificationItemUseCase
import com.example.testproject.app.domain.usecase.notification.UpdateNotificationItemUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    private val addNotificationItemUseCase: AddNotificationItemUseCase,
    private val getNotificationItemUseCase: GetNotificationItemUseCase,
    private val updateNotificationItemUseCase: UpdateNotificationItemUseCase,
    private val clearAllNotificationFromDbUseCase: ClearAllNotificationFromDbUseCase

) : ViewModel() {

    private var _notificationInfo =
        MutableStateFlow<Resource<NotificationDashboard>>(Resource.Loading)
    val notificationInfo = _notificationInfo.asStateFlow()

    private var _notificationInfoAlarm =
        MutableStateFlow<Resource<NotificationDashboard>>(Resource.Loading)
    val notificationInfoAlarm = _notificationInfoAlarm.asStateFlow()


    fun getNotificationItem(id: Int, idUser: String) {
        viewModelScope.launch {
            try {
                _notificationInfo.value = Resource.Loading
                val data = getNotificationItemUseCase.invoke(id, idUser)
                _notificationInfo.value = Resource.Success(data)

            } catch (e: Exception) {
                _notificationInfo.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun getNotificationItem(name: String) {
        viewModelScope.launch {
            try {
                delay(3500)
                _notificationInfoAlarm.value = Resource.Loading
                val data = getNotificationItemUseCase.invoke(name)
                _notificationInfoAlarm.value = Resource.Success(data)
            } catch (e: Exception) {
                _notificationInfoAlarm.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun addNotificationItem(
        idUser: String, name: String, hour: Int, minute: Int, days: List<String>, countDay: Int
    ) {
        viewModelScope.launch {
            val notificationItemDashboard = NotificationDashboard(
                idUser, name, hour, minute, days, countDay
            )
            addNotificationItemUseCase.invoke(notificationItemDashboard)
        }
    }

    fun updateNotificationItem(
        id: Int, idUser: String, name: String, hour: Int, minute: Int, days: List<String>, countDay: Int
    ) {
        viewModelScope.launch {
            val notificationItem = NotificationDashboard(
                idUser, name, hour, minute, days, countDay, id
            )
            updateNotificationItemUseCase.invoke(notificationItem)
        }
    }

    fun clearAllNotification() {
        viewModelScope.launch {
            clearAllNotificationFromDbUseCase.invoke()
        }
    }
}