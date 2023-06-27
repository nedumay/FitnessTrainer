package com.example.testproject.app.domain.model.notification

import java.time.DayOfWeek
import java.util.UUID

data class NotificationDashboard(
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
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
