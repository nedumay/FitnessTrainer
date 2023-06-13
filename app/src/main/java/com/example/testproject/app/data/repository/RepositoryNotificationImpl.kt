package com.example.testproject.app.data.repository

import android.app.Notification
import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Repository interface for work with Notification.
 */
class RepositoryNotificationImpl @Inject constructor() : RepositoryNotification {
    override suspend fun addNotificationItem(notificationItem: Notification) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNotificationItem(notificationItem: Notification) {
        TODO("Not yet implemented")
    }

    override suspend fun editNotificationItem(notificationItem: Notification) {
        TODO("Not yet implemented")
    }

    override suspend fun getNotificationItem(id: Int): Notification {
        TODO("Not yet implemented")
    }

    override fun getNotificationList(): List<Notification> {
        TODO("Not yet implemented")
    }
}