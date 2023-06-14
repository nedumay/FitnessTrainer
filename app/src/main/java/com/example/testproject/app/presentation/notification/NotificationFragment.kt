package com.example.testproject.app.presentation.notification

import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.utils.NotificationService
import com.example.testproject.app.utils.WorkoutNotificationWorker
import com.example.testproject.databinding.FragmentNotificationBinding
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding: FragmentNotificationBinding
        get() = _binding ?: throw RuntimeException("FragmentNotificationBinding == null")


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
    ViewModelProvider(this, viewModelFactory)[NotificationViewModel::class.java]
    }

    private val calendar = Calendar.getInstance()
    private var count = 0
    private var timeFormat = "00:00"
    private lateinit var currentUserId: String
    private lateinit var userIdSharedPreferences: SharedPreferences

    private lateinit var mondayId: UUID
    private lateinit var tuesdayId: UUID
    private lateinit var wednesdayId: UUID
    private lateinit var thursdayId: UUID
    private lateinit var fridayId: UUID
    private lateinit var saturdayId: UUID
    private lateinit var sundayId: UUID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@NotificationFragment)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Получаем id пользователя
        userIdSharedPreferences = requireActivity()
            .getSharedPreferences(
                USER_SHARED_PREF,
                AppCompatActivity.MODE_PRIVATE
            )
        currentUserId = userIdSharedPreferences.getString(USER_ID, null) ?: ""
        initDefaultDay()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDefaultTime()


        binding.imageButtonArrowBack.setOnClickListener {
            launchBackDashboard()
        }
        binding.addTime.setOnClickListener {
            setTime()
        }
        setDay()

        binding.createNotification.setOnClickListener {
            timeFormat = String.format(
                "%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)
            )
            viewModel.addNotificationItem(
                idUser = currentUserId,
                time = timeFormat,
                countDay = count.toString(),
                Monday = mondayId,
                Tuesday = tuesdayId,
                Wednesday = wednesdayId,
                Thursday = thursdayId,
                Friday = fridayId,
                Saturday = saturdayId,
                Sunday = sundayId
            )
            launchBackDashboard()
        }
        binding.clearAllNotifications.setOnClickListener {
            cancelAllNotifications()
        }

        onBackFragment()
    }

    private fun initDefaultDay(){
        mondayId = UUID.fromString(DEFAULT_UUID)
        tuesdayId = UUID.fromString(DEFAULT_UUID)
        wednesdayId = UUID.fromString(DEFAULT_UUID)
        thursdayId = UUID.fromString(DEFAULT_UUID)
        fridayId = UUID.fromString(DEFAULT_UUID)
        saturdayId = UUID.fromString(DEFAULT_UUID)
        sundayId = UUID.fromString(DEFAULT_UUID)
    }

    private fun onBackFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    launchBackDashboard()
                }
            })
    }

    private fun launchBackDashboard() {
        findNavController().navigate(R.id.action_notificationFragment_to_dashboardFragment)
    }

    private fun cancelAllNotifications() {
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
        viewModel.clearAllNotification()
        Toast.makeText(
            requireContext(),
            requireContext().getText(R.string.all_notification_canceled),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initDefaultTime() {
        val timeFormat = String.format(
            "%02d:%02d",
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )
        binding.textViewTime.text = timeFormat
    }

    private fun createNotification() : UUID {
        val notificationIntent = Intent(requireContext(), WorkoutNotificationWorker::class.java)
        val currentTimeMills = System.currentTimeMillis()
        val delay = calendar.timeInMillis - currentTimeMills
        val notificationRequest: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<WorkoutNotificationWorker>()
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .addTag(calendar.get(Calendar.DAY_OF_WEEK).toString())
                .setInputData(
                    workDataOf(
                        "notification_id" to calendar.get(Calendar.DAY_OF_WEEK)
                    )
                )
                .build()
        Log.d("NotificationEnqueue", "created tag: ${notificationRequest.id}")
        NotificationService(notificationRequest).onStartCommand(
            notificationIntent,
            0,
            calendar.get(Calendar.DAY_OF_WEEK)
        )
        return notificationRequest.id
    }

    // Delete notification by tag from enqueue
    private fun deleteEnqueueNotification(uid: UUID) {
        WorkManager.getInstance(requireContext()).cancelWorkById(uid)
        Log.d("NotificationEnqueue", "delete $uid")
    }

    private fun setDay() {
        binding.textViewCountDay.text = "$count/7"
        binding.chipMo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.chipMo.isChecked = true
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                Log.d("NotificationEnqueue", "selected ${calendar.get(Calendar.DAY_OF_WEEK)}")
                mondayId = createNotification()
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipMo.isChecked = false
                deleteEnqueueNotification(mondayId)
                mondayId = UUID.fromString("1")
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
                Log.d("NotificationEnqueue", "added ${calendar.get(Calendar.DAY_OF_WEEK)}")
                tuesdayId = createNotification()
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipTu.isChecked = false
                deleteEnqueueNotification(tuesdayId)
                tuesdayId = UUID.fromString("1")
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
                Log.d("NotificationEnqueue", "added ${calendar.get(Calendar.DAY_OF_WEEK)}")
                wednesdayId = createNotification()
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipWed.isChecked = false
                deleteEnqueueNotification(wednesdayId)
                wednesdayId = UUID.fromString("1")
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
                Log.d("NotificationEnqueue", "added ${calendar.get(Calendar.DAY_OF_WEEK)}")
                thursdayId = createNotification()
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipTh.isChecked = false
                deleteEnqueueNotification(thursdayId)
                thursdayId = UUID.fromString("1")
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
                Log.d("NotificationEnqueue", "added ${calendar.get(Calendar.DAY_OF_WEEK)}")
                fridayId = createNotification()
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipFr.isChecked = false
                deleteEnqueueNotification(fridayId)
                fridayId = UUID.fromString("1")
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
                Log.d("NotificationEnqueue", "added ${calendar.get(Calendar.DAY_OF_WEEK)}")
                saturdayId = createNotification()
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipSa.isChecked = false
                deleteEnqueueNotification(saturdayId)
                saturdayId = UUID.fromString("1")
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
                Log.d("NotificationEnqueue", "added ${calendar.get(Calendar.DAY_OF_WEEK)}")
                sundayId = createNotification()
                if (count != 7) {
                    count++
                    binding.textViewCountDay.text = "$count/7"
                }
            } else {
                binding.chipSu.isChecked = false
                deleteEnqueueNotification(sundayId)
                sundayId = UUID.fromString("1")
                if (count != 0) {
                    count--
                    binding.textViewCountDay.text = "$count/7"
                }
            }
        }
    }

    private fun setTime() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minuteOfDay ->
                val timeFormat = String.format("%02d:%02d", hourOfDay, minuteOfDay)
                binding.textViewTime.text = timeFormat
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minuteOfDay)
                calendar.set(Calendar.SECOND, 0)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val USER_SHARED_PREF = "userPreferences"
        private const val USER_ID = "userId"
        private const val DEFAULT_UUID = "d3cfe541-7001-4d6c-aa8a-55f649867d1e"
    }
}