package com.example.testproject.app.presentation.splash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
        get() = _binding ?: throw RuntimeException("FragmentSplashBinding == null")

    private lateinit var notificationManager : NotificationManagerCompat

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).component.inject(this@SplashFragment)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        launchLoginFragment()
    }

    private fun launchLoginFragment() {
        notificationManager = NotificationManagerCompat.from(requireContext())
        if (notificationManager.areNotificationsEnabled()) {
            // Notifications are allowed, launch login activity
            Handler().postDelayed(Runnable {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment) },
                2000
            )
        } else {
            // Notifications are not allowed, permission must be requested
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        Log.d("SplashFragment", "onDestroyView")
        super.onDestroyView()
        _binding = null
    }

}