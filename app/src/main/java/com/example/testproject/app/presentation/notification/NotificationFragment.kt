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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.utils.AlarmScheduler
import com.example.testproject.app.utils.NotificationReceiver
import com.example.testproject.databinding.FragmentNotificationBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val calendar = Calendar.getInstance(Locale.getDefault())
    private var countDay = 0
    private var countWeek = 0
    private var timeFormat = "00:00"

    private var id: Int? = null
    private var screenMode: String? = GET_MODE_UNKNOWN

    private lateinit var currentUserId: String
    private lateinit var userIdSharedPreferences: SharedPreferences

    private var listDays: MutableList<String> = mutableListOf()
    private var monday: String = DEFAULT_NOTIFICATION_ID
    private var tuesday: String = DEFAULT_NOTIFICATION_ID
    private var wednesday: String = DEFAULT_NOTIFICATION_ID
    private var thursday: String = DEFAULT_NOTIFICATION_ID
    private var friday: String = DEFAULT_NOTIFICATION_ID
    private var saturday: String = DEFAULT_NOTIFICATION_ID
    private var sunday: String = DEFAULT_NOTIFICATION_ID

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
                EDIT -> {
                    it.getInt(GET_NOTIFICATION_ITEM_ID)
                }

                ADD -> {
                    null
                }

                else -> {
                    throw RuntimeException("Screen mode is unknown")
                }
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
            listDays = mutableListOf("", "", "", "", "", "", "")
        } else if (screenMode == EDIT) {
            viewModel.getNotificationItem(id ?: 0, currentUserId)
            viewModel.notificationInfo.onEach {
                when (it) {
                    is Resource.Loading -> {
                        initAddDefaultTime()
                        initAddDefaultDay()
                    }

                    is Resource.Success -> {
                        initEditTime(it.data)
                        initEditDay(it.data)
                        countDay = it.data.countDay
                        listDays = it.data.days as MutableList<String>
                        countWeek = it.data.countWeek
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
                    countWeek = calendar.get(Calendar.WEEK_OF_YEAR)
                    val name = calendar.time.toString()
                    viewModel.addNotificationItem(
                        idUser = currentUserId,
                        name = name,
                        hour = calendar.get(Calendar.HOUR_OF_DAY),
                        minute = calendar.get(Calendar.MINUTE),
                        days = listDays,
                        countDay = countDay,
                        countWeek = countWeek
                    )
                    viewModel.getNotificationItem(name)
                    viewModel.notificationInfoAlarm.onEach {
                        when (it) {
                            is Resource.Loading -> {
                                // Исправить!!!
                            }
                            is Resource.Success -> {
                                Log.d("NotificationCreateAlarm", "AlarmScheduler.scheduleAlarmsForReminder: ${it.data}")
                                AlarmScheduler.scheduleAlarmsForReminder(requireContext().applicationContext, it.data)
                            }
                            is Resource.Error -> {
                                Log.d("NotificationCreateAlarm", "${it.message}")
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }.launchIn(lifecycleScope)
                }

                EDIT -> {
                    timeFormat = String.format(
                        "%02d:%02d",
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE)
                    )
                    val name = calendar.time.toString()
                    viewModel.updateNotificationItem(
                        id = id ?: 0,
                        idUser = currentUserId,
                        name = name,
                        hour = calendar.get(Calendar.HOUR_OF_DAY),
                        minute = calendar.get(Calendar.MINUTE),
                        days = listDays,
                        countDay = countDay,
                        countWeek = countWeek
                    )
                    val notificationDashboard = NotificationDashboard(
                        id = id ?: 0,
                        idUser = currentUserId,
                        name = name,
                        hour = calendar.get(Calendar.HOUR_OF_DAY),
                        minute = calendar.get(Calendar.MINUTE),
                        days = listDays,
                        countDay = countDay,
                        countWeek = countWeek
                    )
                    AlarmScheduler.scheduleAlarmsForReminder(requireContext().applicationContext, notificationDashboard)
                }
            }
            launchBackDashboard()
        }

        binding.clearAllNotifications.setOnClickListener {
            cancelAllNotifications()
            launchBackDashboard()
        }

    }

    private fun initEditDay(it: NotificationDashboard) {
        sunday = it.days[0]
        if (sunday != DEFAULT_NOTIFICATION_ID) {
            binding.chipSu.isChecked = true
        }
        monday = it.days[1]
        if (monday != DEFAULT_NOTIFICATION_ID) {
            binding.chipMo.isChecked = true
        }
        tuesday = it.days[2]
        if (tuesday != DEFAULT_NOTIFICATION_ID) {
            binding.chipTu.isChecked = true
        }
        wednesday = it.days[3]
        if (wednesday != DEFAULT_NOTIFICATION_ID) {
            binding.chipWed.isChecked = true
        }
        thursday = it.days[4]
        if (thursday != DEFAULT_NOTIFICATION_ID) {
            binding.chipTh.isChecked = true
        }
        friday = it.days[5]
        if (friday != DEFAULT_NOTIFICATION_ID) {
            binding.chipFr.isChecked = true
        }
        saturday = it.days[6]
        if (saturday != DEFAULT_NOTIFICATION_ID) {
            binding.chipSa.isChecked = true
        }
    }

    private fun initEditTime(it: NotificationDashboard) {
        timeFormat = String.format(
            "%02d:%02d",
            it.hour,
            it.minute
        )
        binding.textViewTime.text = timeFormat
    }

    private fun initAddDefaultDay() {

        monday = DEFAULT_NOTIFICATION_ID
        tuesday = DEFAULT_NOTIFICATION_ID
        wednesday = DEFAULT_NOTIFICATION_ID
        thursday = DEFAULT_NOTIFICATION_ID
        friday = DEFAULT_NOTIFICATION_ID
        saturday = DEFAULT_NOTIFICATION_ID
        sunday = DEFAULT_NOTIFICATION_ID
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
                NotificationManagerCompat.from(this.requireContext())
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
    private fun setDay() {
        binding.textViewCountDay.text = "$countDay/7"

        binding.chipSa.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && saturday == DEFAULT_NOTIFICATION_ID) {
                saturday = resources.getStringArray(R.array.days)[0]
                listDays[0] = saturday
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if (!isChecked) {
                saturday = DEFAULT_NOTIFICATION_ID
                listDays[0] = ""
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }

        binding.chipSu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && sunday == DEFAULT_NOTIFICATION_ID) {
                sunday = resources.getStringArray(R.array.days)[1]
                listDays[1] = sunday
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if (!isChecked) {
                sunday = DEFAULT_NOTIFICATION_ID
                listDays[1] = ""
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }

        binding.chipMo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && monday == DEFAULT_NOTIFICATION_ID) {
                monday = resources.getStringArray(R.array.days)[2]
                listDays[2] = monday
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if (!isChecked) {
                monday = DEFAULT_NOTIFICATION_ID
                listDays[2] = ""
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipTu.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && tuesday == DEFAULT_NOTIFICATION_ID) {
                tuesday = resources.getStringArray(R.array.days)[3]
                listDays[3] = tuesday
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if (!isChecked) {
                tuesday = DEFAULT_NOTIFICATION_ID
                listDays[3] = ""
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipWed.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && wednesday == DEFAULT_NOTIFICATION_ID) {
                wednesday = resources.getStringArray(R.array.days)[4]
                listDays[4] = wednesday
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if (!isChecked) {
                wednesday = DEFAULT_NOTIFICATION_ID
                listDays[4] = ""
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipTh.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && thursday == DEFAULT_NOTIFICATION_ID) {
                thursday = resources.getStringArray(R.array.days)[5]
                listDays[5] = thursday
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if (!isChecked) {
                thursday = DEFAULT_NOTIFICATION_ID
                listDays[5] = ""
                if (countDay != 0) {
                    countDay--
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            }
        }


        binding.chipFr.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && friday == DEFAULT_NOTIFICATION_ID) {
                friday = resources.getStringArray(R.array.days)[6]
                listDays[6] = friday
                if (countDay != 7) {
                    countDay++
                    binding.textViewCountDay.text = "$countDay/7"
                    binding.createNotification.isEnabled = countDay != 0
                }
            } else if (!isChecked) {
                friday = DEFAULT_NOTIFICATION_ID
                listDays[6] = ""
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
        private const val DEFAULT_NOTIFICATION_ID = ""
        private const val CLEAR_TIME = "00:00"

        private const val GET_MODE = "mode"
        private const val EDIT = "edit"
        private const val ADD = "add"
        private const val GET_MODE_UNKNOWN = ""
        private const val GET_NOTIFICATION_ITEM_ID = "notification_item_id"
    }
}