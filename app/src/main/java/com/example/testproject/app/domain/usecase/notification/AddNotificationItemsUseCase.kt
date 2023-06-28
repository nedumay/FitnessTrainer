package com.example.testproject.app.domain.usecase.notification

import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class AddNotificationItemsUseCase @Inject constructor(private val repository: RepositoryNotification) {

    suspend operator fun invoke(notificationItems: List<NotificationDashboard>) = repository.addNotificationItems(notificationItems)
}