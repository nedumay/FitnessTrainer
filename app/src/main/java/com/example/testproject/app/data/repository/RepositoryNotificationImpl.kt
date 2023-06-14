package com.example.testproject.app.data.repository

import android.view.SurfaceControl.Transaction
import android.view.animation.Transformation
import com.example.testproject.app.data.database.NotificationDao
import com.example.testproject.app.data.mapper.MapperNotification
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.domain.repository.RepositoryNotification
import javax.inject.Inject

/**
 * @author Nedumayy (Samim)
 * Repository interface for work with Notification.
 */
class RepositoryNotificationImpl @Inject constructor(
    private val mapper: MapperNotification,
    private val notificationDao: NotificationDao
) : RepositoryNotification {
    override suspend fun addNotificationItem(notificationItem: NotificationDashboard) {
        notificationDao.addNotificationItem(mapper.mapEntityToDbModel(notificationItem))
    }

    override suspend fun deleteNotificationItem(notificationItem: NotificationDashboard) {
        notificationDao.deleteNotificationItem(notificationItem.tagNotification)
    }

    override suspend fun editNotificationItem(notificationItem: NotificationDashboard) {
        notificationDao.addNotificationItem(mapper.mapEntityToDbModel(notificationItem))
    }

    override suspend fun getNotificationItem(id: Int): NotificationDashboard {
        val dbModel = notificationDao.getNotificationItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun getNotificationList(idUser: String): List<NotificationDashboard> {
        val dbModel = notificationDao.getNotificationList(idUser)
        return dbModel.map{ mapper.mapDbModelToEntity(it) }
    }

    override suspend fun clearDataBase() {
        notificationDao.clearDataBase()
    }
}