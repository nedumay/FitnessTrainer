package ru.nedumayy.fitnesstrainer.app.domain.usecase.notification

import ru.nedumayy.fitnesstrainer.app.domain.model.notification.NotificationDashboard
import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class AddNotificationItemUseCase @Inject constructor(private val repository: RepositoryNotification) {

    suspend operator fun invoke(notificationItem: NotificationDashboard) = repository.addNotificationItem(notificationItem)

}