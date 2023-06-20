package com.example.testproject.app.utils

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

/**
 * @author Nedumayy (Samim)
 * Service for sending notifications
 * Start worker for sending notifications
 * Return result START_STICKY
 */

class NotificationService(private val workRequest: OneTimeWorkRequest) : Service() {

    constructor() : this(OneTimeWorkRequest.Builder(WorkoutNotificationWorker::class.java).build())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Запускаем воркер для отправки уведомлений
        WorkManager.getInstance(this).enqueue(workRequest)
        Log.d("NotificationEnqueue", "onStartService ${workRequest.id}")
        // Возвращаем значение START_STICKY, чтобы сервис продолжал работу после того, как приложение закрыто
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): Binder? {
        return null
    }

}