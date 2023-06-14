package com.example.testproject.app.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.usecase.notification.AddNotificationItemUseCase
import com.example.testproject.app.domain.usecase.notification.ClearAllNotificationFromDbUseCase
import com.example.testproject.app.domain.usecase.notification.EditNotificationItemUseCase
import com.example.testproject.app.domain.usecase.notification.GetNotificationItemUseCase
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class NotificationViewModel @Inject constructor(
    private val addNotificationItemUseCase: AddNotificationItemUseCase,
    private val getNotificationItemUseCase: GetNotificationItemUseCase,
    private val editNotificationItemUseCase: EditNotificationItemUseCase,
    private val clearAllNotificationFromDbUseCase: ClearAllNotificationFromDbUseCase

): ViewModel() {

    fun getNotificationItem(id: Int) {
        viewModelScope.launch {
            getNotificationItemUseCase.invoke(id)
        }
    }

    fun addNotificationItem(
        idUser: String, time: String, countDay: String,
        Monday: UUID, Tuesday: UUID, Wednesday: UUID, Thursday: UUID,
        Friday: UUID, Saturday: UUID, Sunday: UUID
    ) {
        viewModelScope.launch {
            val notificationItemDashboard = NotificationDashboard(
                idUser, time, countDay, Monday, Tuesday, Wednesday, Thursday,
                Friday, Saturday, Sunday
            )
            addNotificationItemUseCase.invoke(notificationItemDashboard)
        }
    }

    fun editNotificationItem(notificationItem: NotificationDashboard) {
        viewModelScope.launch {
            editNotificationItemUseCase.invoke(notificationItem)
        }
    }

    fun clearAllNotification() {
        viewModelScope.launch {
            clearAllNotificationFromDbUseCase.invoke()
        }
    }
}