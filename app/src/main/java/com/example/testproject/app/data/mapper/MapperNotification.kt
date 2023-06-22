package com.example.testproject.app.data.mapper

import com.example.testproject.app.data.database.model.NotificationDashboardDbModel
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import java.util.UUID.fromString
import javax.inject.Inject

class MapperNotification @Inject constructor() {

    fun mapDbModelToEntity(dbModel: NotificationDashboardDbModel) = NotificationDashboard(
        id = dbModel.id,
        idUser = dbModel.idUser,
        time = dbModel.time,
        countDay = dbModel.countDay,
        Monday = dbModel.Monday,
        Tuesday = dbModel.Tuesday,
        Wednesday = dbModel.Wednesday,
        Thursday = dbModel.Thursday,
        Friday = dbModel.Friday,
        Saturday = dbModel.Saturday,
        Sunday = dbModel.Sunday
    )

    fun mapEntityToDbModel(notification:  NotificationDashboard) = NotificationDashboardDbModel(
        id = notification.id,
        idUser = notification.idUser,
        time = notification.time,
        countDay = notification.countDay,
        Monday = notification.Monday,
        Tuesday = notification.Tuesday,
        Wednesday = notification.Wednesday,
        Thursday = notification.Thursday,
        Friday = notification.Friday,
        Saturday = notification.Saturday,
        Sunday = notification.Sunday
    )
}