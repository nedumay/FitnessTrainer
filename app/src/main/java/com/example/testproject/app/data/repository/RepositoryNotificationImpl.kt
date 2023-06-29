package com.example.testproject.app.data.repository

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

    override suspend fun addNotificationItems(notificationItems: List<NotificationDashboard>) {
        val dbModels = notificationItems.map {
            mapper.mapEntityToDbModel(it)
        }
        notificationDao.addNotificationItems(dbModels)
    }

    override suspend fun deleteNotificationItem(notificationItem: NotificationDashboard) {
        notificationDao.deleteNotificationItem(notificationItem.id)
    }

    override suspend fun updateNotificationItem(notificationItem: NotificationDashboard) {
        notificationDao.updateNotificationItem(mapper.mapEntityToDbModel(notificationItem))
    }

    /*
    override suspend fun editNotificationItem(notificationItem: NotificationDashboard) {
        notificationDao.addNotificationItem(mapper.mapEntityToDbModel(notificationItem))
    }
    */


    override suspend fun getNotificationItem(id: Int, idUser: String): NotificationDashboard {
        val dbModel = notificationDao.getNotificationItem(id, idUser)
            ?: return NotificationDashboard("", "", 0, 0, listOf(), 0)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun getNotificationItem(id: Int): NotificationDashboard {
        return mapper.mapDbModelToEntity(notificationDao.getNotificationItem(id))
    }

    override suspend fun getNotificationItem(name: String): NotificationDashboard {
        return mapper.mapDbModelToEntity(notificationDao.getNotificationItem(name))
    }

    override suspend fun getNotificationList(idUser: String): List<NotificationDashboard> {
        val dbModel = notificationDao.getNotificationList(idUser)
        return dbModel.map { mapper.mapDbModelToEntity(it) }
    }

    override suspend fun clearDataBase() {
        notificationDao.clearDataBase()
    }
}