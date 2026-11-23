package ru.nedumayy.fitnesstrainer.app.domain.usecase.notification

import android.app.Notification
import ru.nedumayy.fitnesstrainer.app.domain.model.notification.NotificationDashboard
import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class DeleteNotificationItemUseCase @Inject constructor(private val repository: RepositoryNotification) {

    suspend operator fun invoke(notificationItem: NotificationDashboard) =
        repository.deleteNotificationItem(notificationItem)
}