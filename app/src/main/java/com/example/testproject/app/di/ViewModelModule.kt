package com.example.testproject.app.di

import androidx.lifecycle.ViewModel
import com.example.testproject.app.presentation.registration.four.RegistrationFourViewModel
import com.example.testproject.app.presentation.reset.ResetViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationFourViewModel::class)
    fun bindRegistrationFourViewModel(viewModel: RegistrationFourViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResetViewModel::class)
    fun bindResetViewModel(viewModel: ResetViewModel) : ViewModel
}