package com.example.testproject.app.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.testproject.R
import com.example.testproject.app.common.NOTIFICATION_ITEM_COUNT_DAY_KEY
import com.example.testproject.app.common.NOTIFICATION_ITEM_COUNT_WEEK_KEY
import com.example.testproject.app.common.NOTIFICATION_ITEM_DAYS_KEY
import com.example.testproject.app.common.NOTIFICATION_ITEM_HOUR_KEY
import com.example.testproject.app.common.NOTIFICATION_ITEM_ID_USER_KEY
import com.example.testproject.app.common.NOTIFICATION_ITEM_MINUTE_KEY
import com.example.testproject.app.common.NOTIFICATION_ITEM_NAME_KEY
import com.example.testproject.app.common.NOTIFICATION_ITEM_ID_KEY
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import java.util.Calendar
import java.util.Locale

object AlarmScheduler {

    fun scheduleAlarmsForReminder(context: Context, notificationDashboard: NotificationDashboard) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val days = context.resources.getStringArray(R.array.days)
        if (notificationDashboard.days.isNotEmpty()) {
            for (index in notificationDashboard.days.indices) {
                val day = notificationDashboard.days[index]
                if (day.isNotEmpty()) {
                    val alarmIntent = createPendingIntent(context, notificationDashboard, day)
                    val dayOfWeek = getDayOfWeek(days, day)
                    scheduleAlarm(notificationDashboard, dayOfWeek, alarmIntent, alarmMgr)
                }
            }
        }
    }

    private fun getDayOfWeek(days: Array<String>, day: String): Int {
        return when {
            day.equals(days[0], ignoreCase = true) -> Calendar.SUNDAY
            day.equals(days[1], ignoreCase = true) -> Calendar.MONDAY
            day.equals(days[2], ignoreCase = true) -> Calendar.TUESDAY
            day.equals(days[3], ignoreCase = true) -> Calendar.WEDNESDAY
            day.equals(days[4], ignoreCase = true) -> Calendar.THURSDAY
            day.equals(days[5], ignoreCase = true) -> Calendar.FRIDAY
            day.equals(days[6], ignoreCase = true) -> Calendar.SATURDAY
            else -> Calendar.SUNDAY
        }

    }

    private fun createPendingIntent(
        context: Context,
        notificationDashboard: NotificationDashboard,
        day: String
    ): PendingIntent {
        Log.d("NotificationCreateAlarm", "notificationDashboard: $notificationDashboard")
        val intent = Intent(context.applicationContext, NotificationReceiver::class.java).apply {
            putExtra(NOTIFICATION_ITEM_ID_KEY, notificationDashboard.id)
            putExtra(NOTIFICATION_ITEM_ID_USER_KEY, notificationDashboard.idUser)
            putExtra(NOTIFICATION_ITEM_NAME_KEY, notificationDashboard.name)
            putExtra(NOTIFICATION_ITEM_HOUR_KEY, notificationDashboard.hour)
            putExtra(NOTIFICATION_ITEM_MINUTE_KEY, notificationDashboard.minute)
            putExtra(NOTIFICATION_ITEM_DAYS_KEY, notificationDashboard.days as ArrayList<String>)
            putExtra(NOTIFICATION_ITEM_COUNT_DAY_KEY, notificationDashboard.countDay)
            putExtra(NOTIFICATION_ITEM_COUNT_WEEK_KEY, notificationDashboard.countWeek)
            type = "$day - ${notificationDashboard.name}"
        }
        return PendingIntent.getBroadcast(
            context,
            notificationDashboard.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun shouldNotifyToday(
        dayOfWeek: Int,
        today: Calendar,
        dataTimeToAlarm: Calendar
    ): Boolean {
        return dayOfWeek == today.get(Calendar.DAY_OF_WEEK) &&
                today.get(Calendar.HOUR_OF_DAY) <= dataTimeToAlarm.get(Calendar.HOUR_OF_DAY) &&
                today.get(Calendar.MINUTE) <= dataTimeToAlarm.get(Calendar.MINUTE)

    }



    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleAlarm(
        notificationDashboard: NotificationDashboard, dayOfWeek: Int,
        alarmIntent: PendingIntent, alarmMgr: AlarmManager
    ) {

        val dataTimeToAlarm = Calendar.getInstance(Locale.getDefault())
        dataTimeToAlarm.timeInMillis = System.currentTimeMillis()
        dataTimeToAlarm.set(Calendar.HOUR_OF_DAY, notificationDashboard.hour)
        dataTimeToAlarm.set(Calendar.MINUTE, notificationDashboard.minute)
        dataTimeToAlarm.set(Calendar.SECOND, 0)
        dataTimeToAlarm.set(Calendar.MILLISECOND, 0)
        dataTimeToAlarm.set(Calendar.DAY_OF_WEEK, dayOfWeek)


        val today = Calendar.getInstance(Locale.getDefault())
        if (shouldNotifyToday(dayOfWeek, today, dataTimeToAlarm)) {
            alarmMgr.setRepeating(
                AlarmManager.RTC_WAKEUP,
                dataTimeToAlarm.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7,
                //(1000 * 60 * 60 * 24 * 7).toLong(),
                alarmIntent
            )
            return
        }
        dataTimeToAlarm.roll(Calendar.WEEK_OF_YEAR, 1)
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            dataTimeToAlarm.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7,
            //(1000 * 60 * 60 * 24 * 7).toLong(),
            alarmIntent
        )
    }
}