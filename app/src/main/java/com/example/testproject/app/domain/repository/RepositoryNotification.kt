package com.example.testproject.app.domain.repository

import com.example.testproject.app.domain.model.notification.NotificationDashboard

/**
 * @author Nedumayy (Samim)
 * Repository interface for work with Notification.
 */
interface RepositoryNotification {

    // Add Notification item
    suspend fun addNotificationItem(notificationItem: NotificationDashboard)
    // Add List of Notification items
    suspend fun addNotificationItems(notificationItems: List<NotificationDashboard>)

    // Delete Notification item
    suspend fun deleteNotificationItem(notificationItem: NotificationDashboard)

    //Update Notification item
    suspend fun updateNotificationItem(notificationItem: NotificationDashboard)

    /*
    // Edit Notification item
    suspend fun editNotificationItem(notificationItem: NotificationDashboard)
     */

    // Get Notification item
    suspend fun getNotificationItem(id: Int, idUser: String): NotificationDashboard

    suspend fun getNotificationItem(id: Int): NotificationDashboard

    suspend fun getNotificationItem(name: String): NotificationDashboard

    // Get Notification list
    suspend fun getNotificationList(idUser: String): List<NotificationDashboard>

    // Clear data base
    suspend fun clearDataBase()
}