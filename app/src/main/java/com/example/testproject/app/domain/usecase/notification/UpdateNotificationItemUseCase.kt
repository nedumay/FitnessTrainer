package com.example.testproject.app.domain.usecase.notification

import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class UpdateNotificationItemUseCase @Inject constructor(private val repository: RepositoryNotification) {

    suspend operator fun invoke(notificationItem: NotificationDashboard) = repository.updateNotificationItem(notificationItem)
}