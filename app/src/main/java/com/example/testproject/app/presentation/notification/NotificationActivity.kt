package com.example.testproject.app.presentation.notification

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.utils.WorkoutNotificationReceiver
import com.example.testproject.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNotificationBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageButtonArrowBack.setOnClickListener {
            finish()
        }
        binding.addTime.setOnClickListener {
            setTime()
        }
        setDay()
        binding.createNotification.setOnClickListener {
            //createNotification()
        }

    }
    /*
    private fun createNotification() {

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, WorkoutNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else{
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY * 7, pendingIntent)
    }*/

    private fun setDay() {
        var count = 0
        binding.textViewCountDay.text = "$count/7"
        binding.chipMo.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                binding.chipMo.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                if(count != 7){
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
                Log.d("NotifActivity", "${calendar.get(Calendar.DAY_OF_WEEK)}")
            } else{
                binding.chipMo.isChecked = false
                if(count != 0){
                    count--
                    binding.textViewCountDay.text = "$count/7"
                }
            }
        }
        binding.chipTu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipTu.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
                Log.d("NotifActivity", "${calendar.get(Calendar.DAY_OF_WEEK)}")
                if(count != 7){
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else{
                binding.chipTu.isChecked = false
                if(count != 0){
                    count--
                    binding.textViewCountDay.text = "$count/7"
                }
            }
        }
        binding.chipWed.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipWed.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
                Log.d("NotifActivity", "${calendar.get(Calendar.DAY_OF_WEEK)}")
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipWed.isChecked = false
                if (count != 0) {
                    count--
                    binding.textViewCountDay.text = "$count/7"
                }
            }
        }
        binding.chipTh.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipTh.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
                Log.d("NotifActivity", "${calendar.get(Calendar.DAY_OF_WEEK)}")
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipTh.isChecked = false
                if (count != 0) {
                    count--
                    binding.textViewCountDay.text = "$count/7"
                }
            }
        }
        binding.chipFr.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipFr.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
                Log.d("NotifActivity", "${calendar.get(Calendar.DAY_OF_WEEK)}")
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipFr.isChecked = false
                if (count != 0) {
                    count--
                    binding.textViewCountDay.text = "$count/7"
                }
            }
        }
        binding.chipSa.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipSa.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                Log.d("NotifActivity", "${calendar.get(Calendar.DAY_OF_WEEK)}")
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipSa.isChecked = false
                if (count != 0) {
                    count--
                    binding.textViewCountDay.text = "$count/7"
                }
            }
        }
        binding.chipSu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipSu.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                Log.d("NotifActivity", "${calendar.get(Calendar.DAY_OF_WEEK)}")
                if(count != 7){
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else{
                binding.chipSu.isChecked = false
                if(count != 0){
                    count--
                    binding.textViewCountDay.text = "$count/7"
                }
            }
        }

    }

    private fun setTime() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minuteOfDay ->
                val timeFormat = String.format("%02d:%02d", hourOfDay, minuteOfDay)
                binding.textViewTime.text = timeFormat
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minuteOfDay)
                calendar.set(Calendar.SECOND, 0)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

}