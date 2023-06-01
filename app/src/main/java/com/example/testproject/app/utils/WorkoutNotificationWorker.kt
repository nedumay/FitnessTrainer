package com.example.testproject.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testproject.R
import com.example.testproject.app.presentation.main.MainActivity


class WorkoutNotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_gia_pay_background)
            .setContentTitle(applicationContext.getText(R.string.app_name))
            .setContentText(applicationContext.getText(R.string.text_notification))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        return Result.success()
    }

    companion object {
        private const val CHANNEL_ID = "workout_id"
        private const val CHANNEL = "workout"
        private const val NOTIFICATION_ID = 887
    }
}