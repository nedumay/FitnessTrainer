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
        Monday = fromString(dbModel.Monday),
        Tuesday = fromString(dbModel.Tuesday),
        Wednesday = fromString(dbModel.Wednesday),
        Thursday = fromString(dbModel.Thursday),
        Friday = fromString(dbModel.Friday),
        Saturday = fromString(dbModel.Saturday),
        Sunday = fromString(dbModel.Sunday)
    )

    fun mapEntityToDbModel(notification:  NotificationDashboard) = NotificationDashboardDbModel(
        id = notification.id,
        idUser = notification.idUser,
        time = notification.time,
        countDay = notification.countDay,
        Monday = notification.Monday.toString(),
        Tuesday = notification.Tuesday.toString(),
        Wednesday = notification.Wednesday.toString(),
        Thursday = notification.Thursday.toString(),
        Friday = notification.Friday.toString(),
        Saturday = notification.Saturday.toString(),
        Sunday = notification.Sunday.toString()
    )
}