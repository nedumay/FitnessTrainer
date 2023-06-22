package com.example.testproject.app.presentation.notification

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.utils.NotificationReceiver
import com.example.testproject.databinding.FragmentNotificationBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Locale
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
    private var countDay = 0
    private var timeFormat = "00:00"

    private var id: Int? = null
    private var screenMode: String? = GET_MODE_UNKNOWN

    private lateinit var currentUserId: String
    private lateinit var userIdSharedPreferences: SharedPreferences

    private var mondayId: Int = DEFAULT_NOTIFICATION_ID
    private var tuesdayId: Int = DEFAULT_NOTIFICATION_ID
    private var wednesdayId: Int = DEFAULT_NOTIFICATION_ID
    private var thursdayId: Int = DEFAULT_NOTIFICATION_ID
    private var fridayId: Int = DEFAULT_NOTIFICATION_ID
    private var saturdayId: Int = DEFAULT_NOTIFICATION_ID
    private var sundayId: Int = DEFAULT_NOTIFICATION_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@NotificationFragment)
        // Get id user from shared preferences
        userIdSharedPreferences = requireActivity()
            .getSharedPreferences(
                USER_SHARED_PREF,
                AppCompatActivity.MODE_PRIVATE
            )
        currentUserId = userIdSharedPreferences.getString(USER_ID, null) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get arguments. If screen mode is ADD, then arguments will be null.
        // If screen mode is EDIT, then arguments will be not null
        arguments?.let {
            screenMode = it.getString(GET_MODE)
            id = when (screenMode) {
                EDIT -> { it.getInt(GET_NOTIFICATION_ITEM_ID) }
                ADD -> { null }
                else -> { throw RuntimeException("Screen mode is unknown") }
            }
        }
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

        if (screenMode == ADD) {
            initAddDefaultTime()
            initAddDefaultDay()
        } else if (screenMode == EDIT) {
            viewModel.getNotificationItem(id!!)
            viewModel.notificationInfo.onEach {
                when (it) {
                    is Resource.Loading -> {
                        initAddDefaultTime()
                        initAddDefaultDay()
                    }

                    is Resource.Success -> {
                        initEditTime(it.data)
                        initEditDay(it.data)
                        countDay = it.data.countDay.toInt()
                        binding.textViewCountDay.text = "$countDay/7"
                        binding.createNotification.isEnabled = countDay != 0
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        launchBackDashboard()
                    }
                }
            }.launchIn(lifecycleScope)
        }

        binding.createNotification.isEnabled = countDay != 0
        setDay()
        onBackFragment()

        binding.imageButtonArrowBack.setOnClickListener {
            launchBackDashboard()
        }

        binding.addTime.setOnClickListener {
            setTime()
        }

        // Create notification item or edit notification item
        binding.createNotification.setOnClickListener {
            when (screenMode) {
                ADD -> {
                    timeFormat = String.format(
                        "%02d:%02d",
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE)
                    )
                    viewModel.addNotificationItem(
                        idUser = currentUserId,
                        time = timeFormat,
                        countDay = countDay.toString(),
                        Monday = mondayId,
                        Tuesday = tuesdayId,
                        Wednesday = wednesdayId,
                        Thursday = thursdayId,
                        Friday = fridayId,
                        Saturday = saturdayId,
                        Sunday = sundayId
                    )
                }

                EDIT -> {
                    timeFormat = String.format(
                        "%02d:%02d",
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE)
                    )
                    viewModel.editNotificationItem(
                        id = id!!,
                        idUser = currentUserId,
                        time = timeFormat,
                        countDay = countDay.toString(),
                        Monday = mondayId,
                        Tuesday = tuesdayId,
                        Wednesday = wednesdayId,
                        Thursday = thursdayId,
                        Friday = fridayId,
                        Saturday = saturdayId,
                        Sunday = sundayId
                    )
                }
            }
            launchBackDashboard()
        }

        binding.clearAllNotifications.setOnClickListener {
            cancelAllNotifications()
        }

    }

    private fun initEditDay(it: NotificationDashboard) {
        mondayId = it.Monday
        if (mondayId != DEFAULT_NOTIFICATION_ID) {
            binding.chipMo.isChecked = true
        }
        tuesdayId = it.Tuesday
        if (tuesdayId != DEFAULT_NOTIFICATION_ID) {
            binding.chipTu.isChecked = true
        }
        wednesdayId = it.Wednesday
        if (wednesdayId != DEFAULT_NOTIFICATION_ID) {
            binding.chipWed.isChecked = true
        }
        thursdayId = it.Thursday
        if (thursdayId != DEFAULT_NOTIFICATION_ID) {
            binding.chipTh.isChecked = true
        }
        fridayId = it.Friday
        if (fridayId != DEFAULT_NOTIFICATION_ID) {
            binding.chipFr.isChecked = true
        }
        saturdayId = it.Saturday
        if (saturdayId != DEFAULT_NOTIFICATION_ID) {
            binding.chipSa.isChecked = true
        }
        sundayId = it.Sunday
        if (sundayId != DEFAULT_NOTIFICATION_ID) {
            binding.chipSu.isChecked = true
        }
    }

    private fun initEditTime(it: NotificationDashboard) {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = format.parse(it.time)
        if (date != null) {
            calendar.time = date
        }
        binding.textViewTime.text = it.time
    }

    private fun initAddDefaultDay() {
        mondayId = DEFAULT_NOTIFICATION_ID
        tuesdayId = DEFAULT_NOTIFICATION_ID
        wednesdayId = DEFAULT_NOTIFICATION_ID
        thursdayId = DEFAULT_NOTIFICATION_ID
        fridayId = DEFAULT_NOTIFICATION_ID
        saturdayId = DEFAULT_NOTIFICATION_ID
        sundayId = DEFAULT_NOTIFICATION_ID
    }

    private fun initAddDefaultTime() {
        val timeFormat = String.format(
            "%02d:%02d",
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )
        binding.textViewTime.text = timeFormat
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

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), NotificationReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
            )

        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }

        viewModel.clearAllNotification()
        initAddDefaultTime()
        clearView()
        Toast.makeText(
            requireContext(),
            requireContext().getText(R.string.all_notification_canceled),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun clearView() {
        binding.chipMo.isChecked = false
        binding.chipTu.isChecked = false
        binding.chipWed.isChecked = false
        binding.chipTh.isChecked = false
        binding.chipFr.isChecked = false
        binding.chipSa.isChecked = false
        binding.chipSu.isChecked = false
        countDay = 0
        binding.textViewTime.text = CLEAR_TIME
    }

    private fun createNotification(): Int {

        val notificationId = System.currentTimeMillis().toInt()

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("notification_id", notificationId)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        // Set day of week, hour, minute, second.
        calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK))
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY))
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE))
        calendar.set(Calendar.SECOND, 0)
        Log.d("TimeAlarmNotification", "${calendar.time}")

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.time.time,
            AlarmManager.INTERVAL_DAY * 7, // Repeat every week
            pendingIntent
        )

        Toast.makeText(
            requireContext(),
            "${requireContext().getText(R.string.created_notification)}"
                    + notificationId.toString(),
            Toast.LENGTH_SHORT
        ).show()
        return notificationId
    }

    private fun cancelNotification(notificationId: Int) {

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("notification_id", notificationId)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        Toast.makeText(
            requireContext(),
            "${requireContext().getText(R.string.сancelled_notification)}"
                    + notificationId.toString(),
            Toast.LENGTH_SHORT
        ).show()
        alarmManager.cancel(pendingIntent)
    }

    private fun setDay() {
        binding.textViewCountDay.text = "$countDay/7"
        binding.chipMo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && mondayId == DEFAULT_NOTIFICATION_ID) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                mondayId = createNotification()
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if(!isChecked && mondayId == DEFAULT_NOTIFICATION_ID) {
                cancelNotification(mondayId)
                mondayId = DEFAULT_NOTIFICATION_ID
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipTu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && tuesdayId == DEFAULT_NOTIFICATION_ID) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
                tuesdayId = createNotification()
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if(!isChecked && mondayId == DEFAULT_NOTIFICATION_ID) {
                cancelNotification(tuesdayId)
                tuesdayId = DEFAULT_NOTIFICATION_ID
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipWed.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && wednesdayId == DEFAULT_NOTIFICATION_ID) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
                wednesdayId = createNotification()
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if(!isChecked && mondayId == DEFAULT_NOTIFICATION_ID) {
                cancelNotification(wednesdayId)
                wednesdayId = DEFAULT_NOTIFICATION_ID
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipTh.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && thursdayId == DEFAULT_NOTIFICATION_ID) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
                thursdayId = createNotification()
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if(!isChecked && mondayId == DEFAULT_NOTIFICATION_ID) {
                cancelNotification(thursdayId)
                thursdayId = DEFAULT_NOTIFICATION_ID
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipFr.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && fridayId == DEFAULT_NOTIFICATION_ID) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
                fridayId = createNotification()
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if(!isChecked && mondayId == DEFAULT_NOTIFICATION_ID) {
                cancelNotification(fridayId)
                fridayId = DEFAULT_NOTIFICATION_ID
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }

        binding.chipSa.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && saturdayId == DEFAULT_NOTIFICATION_ID) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                saturdayId = createNotification()
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if(!isChecked && mondayId == DEFAULT_NOTIFICATION_ID) {
                cancelNotification(saturdayId)
                saturdayId = DEFAULT_NOTIFICATION_ID
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipSu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && sundayId == DEFAULT_NOTIFICATION_ID) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                sundayId = createNotification()
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if(!isChecked && mondayId == DEFAULT_NOTIFICATION_ID) {
                cancelNotification(sundayId)
                sundayId = DEFAULT_NOTIFICATION_ID
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
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
        private const val DEFAULT_NOTIFICATION_ID = 0
        private const val CLEAR_TIME = "00:00"

        private const val GET_MODE = "mode"
        private const val EDIT = "edit"
        private const val ADD = "add"
        private const val GET_MODE_UNKNOWN = ""
        private const val GET_NOTIFICATION_ITEM_ID = "notification_item_id"
    }
}