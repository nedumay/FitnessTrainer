package com.example.testproject.app.domain.model.notification

data class NotificationDashboard(
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
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
