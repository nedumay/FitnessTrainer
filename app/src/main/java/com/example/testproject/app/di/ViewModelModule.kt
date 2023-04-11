package com.example.testproject.app.di

import androidx.lifecycle.ViewModel
import com.example.testproject.app.presentation.dashboard.DashboardViewModel
import com.example.testproject.app.presentation.login.LoginViewModel
import com.example.testproject.app.presentation.registration.four.RegistrationFourViewModel
import com.example.testproject.app.presentation.reset.ResetViewModel
import com.example.testproject.app.presentation.settings.SettingsViewModel
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

}