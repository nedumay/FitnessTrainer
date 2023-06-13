package com.example.testproject.app.domain.usecase.notification

import android.app.Notification
import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

class EditNotificationItemUseCase @Inject constructor(private val repositoryNotification: RepositoryNotification) {

    suspend operator fun invoke(notificationItem: Notification) =
        repositoryNotification.editNotificationItem(notificationItem)
}