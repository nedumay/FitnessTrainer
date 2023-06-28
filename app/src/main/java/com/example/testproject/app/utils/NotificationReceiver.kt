package com.example.testproject.app.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.testproject.app.data.database.AppDataBase
import com.example.testproject.app.data.mapper.MapperNotification
import com.example.testproject.app.data.repository.RepositoryNotificationImpl
import com.example.testproject.app.domain.usecase.notification.GetNotificationItemUseCase

/**
 * @author Nedumayy (Samim).
 * Class NotificationReceiver (Broadcast receiver) for sending notifications when app is disabled.
 * Accepts intent from AlarmManager to get notification id.
 * Val startApp to launch app when notification is clicked.
 */

// Исправить с dagger2 по-другому работает!

class NotificationReceiver : BroadcastReceiver() {

    //Проблема с получением данных и передается не тот id и добавить корутину
    lateinit var getNotificationItemUseCase: GetNotificationItemUseCase

    override fun onReceive(context: Context, intent: Intent) {
        if (context != null && intent != null) {
            getNotificationItemUseCase = GetNotificationItemUseCase(
                RepositoryNotificationImpl(
                    mapper = MapperNotification(),
                    notificationDao = AppDataBase.getInstance(context)
                        .notificationDao()
                )
            )
            if (intent.extras != null) {
                val id = intent.extras!!.getInt(GET_NOTIFICATION_ITEM_ID)
                Log.d("NotificationReceiver", "id: $id")
                val data = getNotificationItemUseCase.invoke(id)
                Log.d("NotificationReceiver", "data: $data")
                if (data != null) {
                    MyNotification.createNotificationForWorkout(context, data)
                }
            }
        }
    }

    companion object {
        private const val GET_NOTIFICATION_ITEM_ID = "notification_item_id"
    }
}

