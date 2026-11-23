package ru.nedumayy.fitnesstrainer.app.domain.usecase.notification

import android.app.Notification
import ru.nedumayy.fitnesstrainer.app.domain.model.notification.NotificationDashboard
import ru.nedumayy.fitnesstrainer.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class EditNotificationItemUseCase @Inject constructor(private val repositoryNotification: RepositoryNotification) {

    /*
    suspend operator fun invoke(notificationItem: NotificationDashboard) =
        repositoryNotification.editNotificationItem(notificationItem)

     */
}