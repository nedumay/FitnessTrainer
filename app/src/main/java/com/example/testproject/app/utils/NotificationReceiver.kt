package com.example.testproject.app.utils

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.testproject.app.domain.model.notification.NotificationDashboard

/**
 * @author Nedumayy (Samim).
 * Class NotificationReceiver (Broadcast receiver) for sending notifications when app is disabled.
 * Accepts intent from AlarmManager to get notification id.
 * Val startApp to launch app when notification is clicked.
 */

class NotificationReceiver : BroadcastReceiver() {

    var data: NotificationDashboard? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationCreateAlarm", "start notification")
        if (intent.extras != null) {
            val id = intent.extras!!.getInt(GET_NOTIFICATION_ITEM_ID)
            val idUser = intent.extras!!.getString(GET_NOTIFICATION_ITEM_ID_USER)
            val name = intent.extras!!.getString(GET_NOTIFICATION_ITEM_NAME)
            val hour = intent.extras!!.getInt(GET_NOTIFICATION_ITEM_HOUR)
            val minute = intent.extras!!.getInt(GET_NOTIFICATION_ITEM_MINUTE)
            val days = intent.extras!!.getStringArrayList(GET_NOTIFICATION_ITEM_DAYS) as List<String>
            val countDay = intent.extras!!.getInt(GET_NOTIFICATION_ITEM_COUNT_DAY)
            val countWeek = intent.extras!!.getInt(GET_NOTIFICATION_ITEM_COUNT_WEEK)
            Log.d("NotificationCreateAlarm", "id: $id")
            data = NotificationDashboard(
                id = id, idUser = idUser ?: "", name = name ?: "", hour = hour, minute = minute,
                days = days, countDay = countDay, countWeek = countWeek)
            Log.d("NotificationCreateAlarm", "data: $data")
            if (data != null) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                MyNotification.createNotificationForWorkout(context, data!!)
            }
        }
    }

    companion object {
        private const val GET_NOTIFICATION_ITEM_ID = "notification_item_id"
        private const val GET_NOTIFICATION_ITEM_ID_USER = "notification_item_id_user"
        private const val GET_NOTIFICATION_ITEM_NAME = "notification_item_name"
        private const val GET_NOTIFICATION_ITEM_HOUR = "notification_item_hour"
        private const val GET_NOTIFICATION_ITEM_MINUTE = "notification_item_minute"
        private const val GET_NOTIFICATION_ITEM_DAYS = "notification_item_days"
        private const val GET_NOTIFICATION_ITEM_COUNT_DAY = "notification_item_count_day"
        private const val GET_NOTIFICATION_ITEM_COUNT_WEEK = "notification_item_count_week"
    }
}

