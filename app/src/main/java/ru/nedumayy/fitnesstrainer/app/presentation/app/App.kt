package ru.nedumayy.fitnesstrainer.app.presentation.app

import android.app.Application
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.util.Log
import ru.nedumayy.fitnesstrainer.R
import ru.nedumayy.fitnesstrainer.app.di.DaggerApplicationComponent
import ru.nedumayy.fitnesstrainer.app.utils.MyNotification
import ru.nedumayy.fitnesstrainer.app.utils.NotificationReceiver
import com.google.firebase.FirebaseApp
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