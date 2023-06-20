package com.example.testproject.app.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "notification_dashboard")
data class NotificationDashboardDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idUser: String,
    val time: String,
    val countDay: String,
    val Monday: Int,
    val Tuesday: Int,
    val Wednesday: Int,
    val Thursday: Int,
    val Friday: Int,
    val Saturday: Int,
    val Sunday: Int,
)
