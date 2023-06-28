package com.example.testproject.app.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.testproject.R
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.presentation.main.MainActivity

object MyNotification {


        private const val CHANNEL_ID = "workout_id"
        private const val CHANNEL = "Reminder workout"

        fun createNotificationChannel(
            context: Context, importance: Int,
            showBadge: Boolean, name: String, description: String
        ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "$CHANNEL_ID-$name"
                val channel = NotificationChannel(channelId, CHANNEL, importance)
                channel.description = description
                channel.setShowBadge(showBadge)

                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }

        fun createNotificationForWorkout(context: Context, notificationDashboard: NotificationDashboard) {

            val notificationBuilder = buildNotification(context, notificationDashboard)
            val notificationManager = NotificationManagerCompat.from(context)
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.notify(notificationDashboard.id, notificationBuilder.build())
        }

        private fun buildNotification(context: Context, notificationDashboard: NotificationDashboard): NotificationCompat.Builder {

            val channelId = "$CHANNEL_ID-${notificationDashboard.name}"

            val startAppIntent = Intent(context, MainActivity::class.java)
            startAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val contentIntent =
                PendingIntent.getActivity(
                    context,
                    notificationDashboard.id,
                    startAppIntent,
                    PendingIntent.FLAG_MUTABLE
                )

            return NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_gia_pay_background)
                .setContentTitle(context.getText(R.string.app_name))
                .setContentText(context.getText(R.string.text_notification))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
        }



}