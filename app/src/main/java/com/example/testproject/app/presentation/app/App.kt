package com.example.testproject.app.presentation.app

import android.app.Application
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.util.Log
import com.example.testproject.R
import com.example.testproject.app.di.DaggerApplicationComponent
import com.example.testproject.app.utils.MyNotification
import com.example.testproject.app.utils.NotificationReceiver
import javax.inject.Inject

class App : Application(){

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()

    }
}