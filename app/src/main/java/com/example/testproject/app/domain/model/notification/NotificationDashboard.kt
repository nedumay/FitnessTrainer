package com.example.testproject.app.domain.model.notification

import java.time.DayOfWeek
import java.util.UUID

data class NotificationDashboard(
    val id: Int,
    val idUser: String,
    val name: String,
    val hour: Int,
    val minute: Int,
    val days: List<String>,
    val countDay: Int,
)
