package ru.nedumayy.fitnesstrainer.app.di

import android.app.Application
import ru.nedumayy.fitnesstrainer.app.presentation.app.App
import ru.nedumayy.fitnesstrainer.app.presentation.dashboard.DashboardFragment
import ru.nedumayy.fitnesstrainer.app.presentation.login.LoginFragment
import ru.nedumayy.fitnesstrainer.app.presentation.main.MainActivity
import ru.nedumayy.fitnesstrainer.app.presentation.notification.NotificationFragment
import ru.nedumayy.fitnesstrainer.app.presentation.registration.four.RegistrationFourFragment
import ru.nedumayy.fitnesstrainer.app.presentation.registration.one.RegistrationOneFragment
import ru.nedumayy.fitnesstrainer.app.presentation.registration.three.RegistrationThreeFragment
import ru.nedumayy.fitnesstrainer.app.presentation.registration.two.RegistrationTwoFragment
import ru.nedumayy.fitnesstrainer.app.presentation.reset.ResetFragment
import ru.nedumayy.fitnesstrainer.app.presentation.settings.SettingsFragment
import ru.nedumayy.fitnesstrainer.app.presentation.splash.SplashFragment
import ru.nedumayy.fitnesstrainer.app.presentation.workout.detail.DetailFragment
import ru.nedumayy.fitnesstrainer.app.presentation.workout.list.ExerciseListFragment
import ru.nedumayy.fitnesstrainer.app.presentation.workout.lvl.LevelFragment
import ru.nedumayy.fitnesstrainer.app.utils.NotificationReceiver
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