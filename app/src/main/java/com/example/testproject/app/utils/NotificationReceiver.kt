package com.example.testproject.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.testproject.R
import com.example.testproject.app.presentation.main.MainActivity

/**
 * @author Nedumayy (Samim).
 * Class NotificationReceiver (Broadcast receiver) for sending notifications when app is disabled.
 * Accepts intent from AlarmManager to get notification id.
 * Val startApp to launch app when notification is clicked.
 */

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val startAppIntent = Intent(context, MainActivity::class.java)
        startAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val contentIntent =
            PendingIntent.getActivity(context, 0, startAppIntent, PendingIntent.FLAG_MUTABLE)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = intent.getIntExtra("notification_id", 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_gia_pay_background)
            .setContentTitle(context.getText(R.string.app_name))
            .setContentText(context.getText(R.string.text_notification))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    companion object {
        private const val CHANNEL_ID = "workout_id"
        private const val CHANNEL = "Reminder workout"
    }
}