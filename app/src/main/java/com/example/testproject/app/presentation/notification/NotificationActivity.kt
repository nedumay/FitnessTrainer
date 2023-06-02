package com.example.testproject.app.presentation.notification

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.utils.WorkoutNotificationWorker
import com.example.testproject.databinding.ActivityNotificationBinding
import java.util.concurrent.TimeUnit

class NotificationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNotificationBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }
    private val calendar = Calendar.getInstance()
    private var count = 0

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
            createNotification()
        }

    }

    private fun createNotification() {

        val currentTimeMills = System.currentTimeMillis()
        val delay = calendar.timeInMillis - currentTimeMills
        val notificationRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<WorkoutNotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag("notification_time")
            .setInputData(
                workDataOf(
                    "notification_id" to calendar.get(Calendar.DAY_OF_WEEK)
                )
            )
            .build()
        addEnqueueNotification(notificationRequest)
    }
    // Add notification to enqueue
    private fun addEnqueueNotification(notification: OneTimeWorkRequest) {
        WorkManager.getInstance(applicationContext).enqueue(notification)
    }
    // Delete notification by tag from enqueue
    private fun deleteEnqueueNotification(tag: String) {
        WorkManager.getInstance(applicationContext).cancelAllWorkByTag(tag)
    }

    private fun setDay() {

        binding.textViewCountDay.text = "$count/7"
        binding.chipMo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipMo.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
                Log.d("NotifActivity", "${calendar.get(Calendar.DAY_OF_WEEK)}")
            } else {
                binding.chipMo.isChecked = false
                if (count != 0) {
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
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipTu.isChecked = false
                if (count != 0) {
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
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipSu.isChecked = false
                if (count != 0) {
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