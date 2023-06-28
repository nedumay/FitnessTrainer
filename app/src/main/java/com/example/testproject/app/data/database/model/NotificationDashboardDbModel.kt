package com.example.testproject.app.data.database.model

import android.os.Parcelable
import android.provider.CalendarContract.EventDays
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Entity(tableName = "notification_dashboard")
@Parcelize
data class NotificationDashboardDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var idUser: String? = null,
    var name: String? = null,
    var hour: Int = 0,
    var minute: Int = 0,
    var days: String? = null,
    var countDay: Int = 0,
) : Parcelable
