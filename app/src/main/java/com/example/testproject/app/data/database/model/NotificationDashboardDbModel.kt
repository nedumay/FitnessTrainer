package com.example.testproject.app.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_dashboard")
data class NotificationDashboardDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idUser: String,
    val time: String,
    val countDay: String,
    val tagNotification: String,
    val Monday: Boolean,
    val Tuesday: Boolean,
    val Wednesday: Boolean,
    val Thursday: Boolean,
    val Friday: Boolean,
    val Saturday: Boolean,
    val Sunday: Boolean,
)
