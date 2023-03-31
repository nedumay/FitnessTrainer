package com.example.testproject.app.di

import android.app.Application
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.dashboard.DashboardActivity
import com.example.testproject.app.presentation.login.LoginActivity
import com.example.testproject.app.presentation.main.MainActivity
import com.example.testproject.app.presentation.notification.NotificationActivity
import com.example.testproject.app.presentation.registration.four.RegistrationFour
import com.example.testproject.app.presentation.registration.one.RegistrationOne
import com.example.testproject.app.presentation.registration.three.RegistrationThree
import com.example.testproject.app.presentation.registration.two.RegistrationTwo
import com.example.testproject.app.presentation.reset.ResetActivity
import com.example.testproject.app.presentation.settings.SettingsActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: LoginActivity)

    fun inject(activity: DashboardActivity)

    fun inject(activity: NotificationActivity)

    fun inject(activity: SettingsActivity)

    fun inject(activity: ResetActivity)

    fun inject(activity: RegistrationOne)

    fun inject(activity: RegistrationTwo)

    fun inject(activity: RegistrationThree)

    fun inject(activity: RegistrationFour)

    fun inject(application: App)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application) : ApplicationComponent
    }


}