package com.example.testproject.app.presentation.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testproject.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNotificationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}