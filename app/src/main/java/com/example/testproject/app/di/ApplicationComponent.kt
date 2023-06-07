package com.example.testproject.app.di

import android.app.Application
import com.example.testproject.app.presentation.app.App
import com.example.testproject.app.presentation.dashboard.DashboardFragment
import com.example.testproject.app.presentation.login.LoginFragment
import com.example.testproject.app.presentation.main.MainActivity
import com.example.testproject.app.presentation.notification.NotificationFragment
import com.example.testproject.app.presentation.registration.four.RegistrationFourFragment
import com.example.testproject.app.presentation.registration.one.RegistrationOneFragment
import com.example.testproject.app.presentation.registration.three.RegistrationThreeFragment
import com.example.testproject.app.presentation.registration.two.RegistrationTwoFragment
import com.example.testproject.app.presentation.reset.ResetFragment
import com.example.testproject.app.presentation.settings.SettingsFragment
import com.example.testproject.app.presentation.splash.SplashFragment
import com.example.testproject.app.presentation.workout.detail.DetailFragment
import com.example.testproject.app.presentation.workout.list.ExerciseListFragment
import com.example.testproject.app.presentation.workout.lvl.LevelFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: SplashFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: DashboardFragment)

    fun inject(fragment: NotificationFragment)

    fun inject(fragment: RegistrationOneFragment)

    fun inject(fragment: RegistrationTwoFragment)

    fun inject(fragment: RegistrationThreeFragment)

    fun inject(fragment: RegistrationFourFragment)

    fun inject(fragment: ResetFragment)

    fun inject(fragment: SettingsFragment)

    fun inject(fragment: DetailFragment)

    fun inject(fragment: ExerciseListFragment)

    fun inject(fragment: LevelFragment)

    fun inject(application: App)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }


}