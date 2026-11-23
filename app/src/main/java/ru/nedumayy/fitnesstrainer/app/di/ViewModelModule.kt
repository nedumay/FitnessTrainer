package ru.nedumayy.fitnesstrainer.app.di

import androidx.lifecycle.ViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.dashboard.DashboardViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.login.LoginViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.notification.NotificationViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.registration.four.RegistrationFourViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.reset.ResetViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.settings.SettingsViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.workout.detail.DetailViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.workout.list.ExercisesViewModel
import ru.nedumayy.fitnesstrainer.app.presentation.workout.lvl.LevelViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationFourViewModel::class)
    fun bindRegistrationFourViewModel(viewModel: RegistrationFourViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResetViewModel::class)
    fun bindResetViewModel(viewModel: ResetViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesViewModel::class)
    fun bindWorkoutsViewModel(viewModel: ExercisesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LevelViewModel::class)
    fun bindLvlViewModel(viewModel: LevelViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    fun bindNotificationViewModel(viewModel: NotificationViewModel): ViewModel

}