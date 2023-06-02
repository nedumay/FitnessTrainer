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
        showWorkoutNotification()
        return Result.success()
    }

    private fun showWorkoutNotification() {
        val notificationId = inputData.getInt("notification_id", 0)
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_gia_pay_background)
            .setContentTitle(applicationContext.getText(R.string.app_name))
            .setContentText(applicationContext.getText(R.string.text_notification))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    companion object {
        private const val CHANNEL_ID = "workout_id"
        private const val CHANNEL = "Reminder workout"
    }
}