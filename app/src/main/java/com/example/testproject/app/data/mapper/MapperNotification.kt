package com.example.testproject.app.data.mapper

import com.example.testproject.app.data.database.model.NotificationDashboardDbModel
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import javax.inject.Inject

class MapperNotification @Inject constructor() {

    fun mapDbModelToEntity(dbModel: NotificationDashboardDbModel) = NotificationDashboard(
        id = dbModel.id,
        idUser = dbModel.idUser ?: "",
        name = dbModel.name ?: "",
        hour = dbModel.hour ,
        minute = dbModel.minute ,
        days = dbModel.days?.split(SEPARATOR)?.toList() as List<String>,
        countDay = dbModel.countDay,
        countWeek = dbModel.countWeek

    )

    fun mapEntityToDbModel(notification:  NotificationDashboard) = NotificationDashboardDbModel(
        id = notification.id,
        idUser = notification.idUser,
        name = notification.name,
        hour = notification.hour,
        minute = notification.minute,
        days = notification.days.joinToString(SEPARATOR),
        countDay = notification.countDay,
        countWeek = notification.countWeek
    )

    companion object{
        private const val SEPARATOR = ";"
    }
}