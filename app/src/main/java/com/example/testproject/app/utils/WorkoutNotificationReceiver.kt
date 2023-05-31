package com.example.testproject.app.utils

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.testproject.R

class WorkoutNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_gia_pay_background)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(NOTIFICATION_TEXT)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build())


    }

    companion object {
        private const val CHANNEL_ID = "workout_id"
        private const val NOTIFICATION_ID = 887
        private const val NOTIFICATION_TITLE = R.string.app_name.toString()
        private const val NOTIFICATION_TEXT = R.string.app_name.toString()
    }
}