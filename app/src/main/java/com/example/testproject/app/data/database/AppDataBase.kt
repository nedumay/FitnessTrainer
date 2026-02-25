package com.example.testproject.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testproject.app.data.database.model.NotificationDashboardDbModel

/**
 * @author Nedumayy (Samim)
 * Абстрактный класс для инициализации базы данных
 */

@Database(entities = [NotificationDashboardDbModel::class], version = 9, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        private var db : AppDataBase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDataBase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    DB_NAME
                ). fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun notificationDao(): NotificationDao
}