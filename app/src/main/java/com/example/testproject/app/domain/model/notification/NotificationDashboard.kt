package com.example.testproject.app.domain.model.notification

import java.util.UUID

data class NotificationDashboard(
    val idUser: String,
    val time: String,
    val countDay: String,
    val Monday: UUID,
    val Tuesday: UUID,
    val Wednesday: UUID,
    val Thursday: UUID,
    val Friday: UUID,
    val Saturday: UUID,
    val Sunday: UUID,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
