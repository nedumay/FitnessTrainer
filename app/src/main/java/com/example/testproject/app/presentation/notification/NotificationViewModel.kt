package com.example.testproject.app.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.usecase.notification.AddNotificationItemUseCase
import com.example.testproject.app.domain.usecase.notification.ClearAllNotificationFromDbUseCase
import com.example.testproject.app.domain.usecase.notification.EditNotificationItemUseCase
import com.example.testproject.app.domain.usecase.notification.GetNotificationItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    private val addNotificationItemUseCase: AddNotificationItemUseCase,
    private val getNotificationItemUseCase: GetNotificationItemUseCase,
    private val editNotificationItemUseCase: EditNotificationItemUseCase,
    private val clearAllNotificationFromDbUseCase: ClearAllNotificationFromDbUseCase

) : ViewModel() {

    private var _notificationInfo =
        MutableStateFlow<Resource<NotificationDashboard>>(Resource.Loading)
    val notificationInfo = _notificationInfo.asStateFlow()


    fun getNotificationItem(id: Int) {
        viewModelScope.launch {
            try {
                _notificationInfo.value = Resource.Loading
                val data = getNotificationItemUseCase.invoke(id)
                _notificationInfo.value = Resource.Success(data)

            } catch (e: Exception) {
                _notificationInfo.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun addNotificationItem(
        idUser: String, time: String, countDay: String,
        Monday: Int, Tuesday: Int, Wednesday: Int, Thursday: Int,
        Friday: Int, Saturday: Int, Sunday: Int
    ) {
        viewModelScope.launch {
            val notificationItemDashboard = NotificationDashboard(
                idUser, time, countDay, Monday, Tuesday, Wednesday, Thursday,
                Friday, Saturday, Sunday
            )
            addNotificationItemUseCase.invoke(notificationItemDashboard)
        }
    }

    fun editNotificationItem(
        id: Int, idUser: String, time: String, countDay: String,
        Monday: Int, Tuesday: Int, Wednesday: Int, Thursday: Int,
        Friday: Int, Saturday: Int, Sunday: Int
    ) {
        viewModelScope.launch {
            val notificationItem = NotificationDashboard(
                idUser, time, countDay, Monday, Tuesday, Wednesday, Thursday,
                Friday, Saturday, Sunday, id
            )
            editNotificationItemUseCase.invoke(notificationItem)
        }
    }

    fun clearAllNotification() {
        viewModelScope.launch {
            clearAllNotificationFromDbUseCase.invoke()
        }
    }
}