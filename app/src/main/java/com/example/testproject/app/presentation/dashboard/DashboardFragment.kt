package com.example.testproject.app.presentation.dashboard

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.common.Resource
import com.example.testproject.app.domain.model.notification.NotificationDashboard
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.dashboard.adapters.NotificationAdapter
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.app.presentation.settings.SettingsFragment
import com.example.testproject.app.utils.NotificationReceiver
import com.example.testproject.databinding.FragmentDashboardBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding
        get() = _binding ?: throw RuntimeException("FragmentDashboardBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]
    }

    private val notificationAdapter by lazy {
        NotificationAdapter()
    }

    private lateinit var currentUserId: String
    private lateinit var userIdSharedPreferences: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@DashboardFragment)
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.firebaseUser.onEach{
            when (it) {
                is Resource.Loading -> {
                    Log.d("Account user", "Loading: $it")
                    binding.cardClickToStart.visibility = View.GONE
                    binding.notificationRecyclerView.visibility = View.GONE
                    binding.addScheduleButton.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("Account user", "Success: $it")
                    binding.cardClickToStart.visibility = View.VISIBLE
                    binding.notificationRecyclerView.visibility = View.VISIBLE
                    binding.addScheduleButton.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error -> {
                    Log.d("Account user", "Error: $it")
                    binding.cardClickToStart.visibility = View.GONE
                    binding.notificationRecyclerView.visibility = View.GONE
                    binding.addScheduleButton.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    launchLoginFragment()
                }

            }

        }.launchIn(lifecycleScope)

    }

    private fun launchLoginFragment() {
        userIdSharedPreferences.edit().clear().apply()
        findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
    }

    override fun onResume() {
        super.onResume()
        appBarMenu()

        viewModel.notificationList.observe(viewLifecycleOwner) {
            binding.notificationRecyclerView.adapter = notificationAdapter
            notificationAdapter.submitList(it)
        }

        binding.cardClickToStart.setOnClickListener {
            launchLevelFragment()
        }

        binding.addScheduleButton.setOnClickListener {
            launchAddNotificationFragment()
        }

        notificationAdapter.onNotificationClickListener = {
            launchEditNotificationFragment(it)
        }

    }

    private fun launchLevelFragment() {
        findNavController().navigate(R.id.action_dashboardFragment_to_levelFragment)
    }

    private fun launchEditNotificationFragment(it: NotificationDashboard) {
        val bundle = Bundle()
        bundle.putString(PUT_MODE, EDIT)
        bundle.putInt(PUT_NOTIFICATION_ITEM_ID, it.id)
        findNavController().navigate(R.id.action_dashboardFragment_to_notificationFragment, bundle)
    }

    private fun launchAddNotificationFragment() {
        val bundle = Bundle()
        bundle.putString(PUT_MODE, ADD)
        findNavController().navigate(R.id.action_dashboardFragment_to_notificationFragment, bundle)
    }

    private fun launchSettingsFragment() {
        findNavController().navigate(R.id.action_dashboardFragment_to_settingsFragment)
    }

    private fun appBarMenu() {

        binding.appBarLayout.setOnClickListener { view ->
            Snackbar.make(view, "Replace with you own action", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.alerts -> {
                    Toast.makeText(requireContext(), "Alerts", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.settings -> {
                    launchSettingsFragment()
                    true
                }

                else -> false
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PUT_MODE = "mode"
        private const val ADD = "add"
        private const val EDIT = "edit"
        private const val PUT_NOTIFICATION_ITEM_ID = "notification_item_id"
        private const val USER_SHARED_PREF = "userPreferences"
        private const val USER_ID = "userId"
    }

}