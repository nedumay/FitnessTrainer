package com.example.testproject.app.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.testproject.R
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.presentation.main.MainActivity

object MyNotification {

    private const val CHANNEL_ID = "workout_id"
    private const val CHANNEL = "Reminder workout"

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun createNotificationForWorkout(
        context: Context,
        notificationDashboard: NotificationDashboard
    ) {

        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "$CHANNEL_ID-${notificationDashboard.name}"
            val channel = NotificationChannel(channelId, CHANNEL, NotificationManager.IMPORTANCE_DEFAULT)
            channel.setShowBadge(false)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = buildNotification(context, notificationDashboard).build()
        notificationManager.notify(1001, notificationBuilder)
    }

    private fun buildNotification(
        context: Context,
        notificationDashboard: NotificationDashboard
    ): NotificationCompat.Builder {

        val channelId = "$CHANNEL_ID-${notificationDashboard.name}"

        val startAppIntent = Intent(context, MainActivity::class.java)
        startAppIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val contentIntent =
            PendingIntent.getActivity(
                context,
                notificationDashboard.id,
                startAppIntent,
                PendingIntent.FLAG_IMMUTABLE
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