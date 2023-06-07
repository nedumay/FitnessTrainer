package com.example.testproject.app.presentation.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.factory.ViewModelFactory
import com.example.testproject.databinding.FragmentDashboardBinding
import com.google.android.material.snackbar.Snackbar
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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@DashboardFragment)
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

        viewModel.firebaseUser.observe(viewLifecycleOwner) {
            Log.d("Account user", "User observe activity: $it")
            if (it == null) {
                findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
            }
        }

        appBarMenu()

        binding.addScheduleButton.setOnClickListener {
             launchNotificationFragment()
        }
        binding.cardClickToStart.setOnClickListener {
            launchLevelFragment()
        }
    }

    private fun launchLevelFragment() {
        findNavController().navigate(R.id.action_dashboardFragment_to_levelFragment)
    }

    private fun launchNotificationFragment() {
        findNavController().navigate(R.id.action_dashboardFragment_to_notificationFragment)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}