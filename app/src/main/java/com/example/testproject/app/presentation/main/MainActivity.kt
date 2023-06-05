package com.example.testproject.app.presentation.main

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.example.testproject.R
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.login.LoginActivity


class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as App).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.areNotificationsEnabled()) {
            Handler().postDelayed(Runnable {
                startActivity(LoginActivity.newIntent(this@MainActivity))
                finish()
            }, 5000)
        } else {
            // Уведомления не разрешены, необходимо запросить разрешение
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            startActivity(intent)
        }



    }

}