package com.example.testproject.app.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App


class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as App).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (notificationManager.areNotificationsEnabled()) {
            // Notifications are allowed, launch login activity
            Handler().postDelayed(Runnable {
                startActivity(LoginActivity.newIntent(this@MainActivity))
                finish()
            }, 5000)
        } else {
            // Notifications are not allowed, permission must be requested
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            startActivity(intent)
        }*/

    }

}