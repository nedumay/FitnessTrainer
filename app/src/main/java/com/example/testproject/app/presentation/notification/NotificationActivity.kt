package com.example.testproject.app.presentation.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testproject.app.presentation.app.App
import com.example.testproject.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNotificationBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}