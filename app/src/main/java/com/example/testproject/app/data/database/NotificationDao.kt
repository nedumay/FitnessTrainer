package com.example.testproject.app.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testproject.app.data.database.model.NotificationDashboardDbModel

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notification_dashboard WHERE idUser =:idUser")
    suspend fun getNotificationList(idUser: String): List<NotificationDashboardDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotificationItem(notificationItem: NotificationDashboardDbModel)

    @Query("DELETE FROM notification_dashboard WHERE tagNotification =:tagNotification")
    suspend fun deleteNotificationItem(tagNotification: String)

    @Query("SELECT * FROM notification_dashboard WHERE id=:id")
    suspend fun getNotificationItem(id: Int): NotificationDashboardDbModel

    @Query("DELETE FROM notification_dashboard")
    suspend fun clearDataBase()

}