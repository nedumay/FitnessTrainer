package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.notification.NotificationDashboard

/**
 * @author Nedumayy (Samim)
 * Repository interface for work with Notification.
 */
interface RepositoryNotification {

    // Add Notification item
    suspend fun addNotificationItem(notificationItem: NotificationDashboard)

    // Delete Notification item
    suspend fun deleteNotificationItem(notificationItem: NotificationDashboard)

    // Edit Notification item
    suspend fun editNotificationItem(notificationItem: NotificationDashboard)

    // Get Notification item
    suspend fun getNotificationItem(id: Int): NotificationDashboard

    // Get Notification list
    suspend fun getNotificationList(idUser: String): List<NotificationDashboard>

    // Clear data base
    suspend fun clearDataBase()
}