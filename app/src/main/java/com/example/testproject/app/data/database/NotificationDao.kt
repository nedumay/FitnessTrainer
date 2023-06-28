package com.example.testproject.app.data.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.testproject.app.data.database.model.NotificationDashboardDbModel

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notification_dashboard WHERE idUser =:idUser")
    suspend fun getNotificationList(idUser: String): List<NotificationDashboardDbModel>

    @Query("SELECT * FROM notification_dashboard WHERE id=:id AND idUser=:idUser")
    suspend fun getNotificationItem(id: Int, idUser: String): NotificationDashboardDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotificationItem(notificationItem: NotificationDashboardDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotificationItems(notificationItems: List<NotificationDashboardDbModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNotificationItem(notificationItem: NotificationDashboardDbModel)

    @Query("DELETE FROM notification_dashboard WHERE id =:id")
    suspend fun deleteNotificationItem(id: Int)


    @Query("SELECT * FROM notification_dashboard WHERE id=:id")
    fun getNotificationItem(id: Int): NotificationDashboardDbModel

    @Query("DELETE FROM notification_dashboard")
    suspend fun clearDataBase()

}