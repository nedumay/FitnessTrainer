package com.example.testproject.app.domain.repository

import android.app.Notification

/**
 * @author Nedumayy (Samim)
 * Repository interface for work with Notification.
 */
interface RepositoryNotification {

    // Add Notification item
    suspend fun addNotificationItem(notificationItem: Notification)
    // Delete Notification item
    suspend fun deleteNotificationItem(notificationItem: Notification)
    // Edit Notification item
    suspend fun editNotificationItem(notificationItem: Notification)
    // Get Notification item
    suspend fun getNotificationItem(id: Int): Notification
    // Get Notification list
    fun getNotificationList(): List<Notification>
}