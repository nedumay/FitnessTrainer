package com.example.testproject.app.domain.model.notification

data class NotificationDashboard(
    val idUser: String,
    val name: String,
    val hour: Int,
    val minute: Int,
    val days: List<String>,
    val countDay: Int,
    val countWeek: Int,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
