package com.example.testproject.app.domain.usecase.notification

import android.app.Notification
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class DeleteNotificationItemUseCase @Inject constructor(private val repository: RepositoryNotification) {

    suspend operator fun invoke(notificationItem: NotificationDashboard) =
        repository.deleteNotificationItem(notificationItem)
}